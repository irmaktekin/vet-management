package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.*;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.exception.InvalidGenderException;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.core.exception.ValidationException;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dao.AnimalVaccineRepository;
import dev.patika.vetmanagement.dto.request.animal.AnimalSaveRequest;
import dev.patika.vetmanagement.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vetmanagement.dto.request.doctor.DoctorSaveRequest;
import dev.patika.vetmanagement.dto.request.vaccineAnimal.VacinneAnimalSaveRequest;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import dev.patika.vetmanagement.dto.response.animal.AnimalResponse;
import dev.patika.vetmanagement.dto.response.customer.CustomerSaveResponse;
import dev.patika.vetmanagement.dto.response.doctor.DoctorResponse;
import dev.patika.vetmanagement.entities.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService iAnimalService;
    private final IModelMapperService iModelMapperService;
    private final IDoctorService iDoctorService;
    private final ICustomerService iCustomerService;
    private final IVaccineService iVaccineService;
    private final IAnimalVaccineService iAnimalVaccineService;
    private final LocalDateTime now = LocalDateTime.now(); // Current time for comparison


    public AnimalController(IAnimalService iAnimalService, IModelMapperService iModelMapperService, IDoctorService iDoctorService, ICustomerService iCustomerService, IVaccineService iVaccineService, IAnimalVaccineService iAnimalVaccineService) {
        this.iAnimalService = iAnimalService;
        this.iModelMapperService = iModelMapperService;
        this.iDoctorService = iDoctorService;
        this.iCustomerService = iCustomerService;
        this.iVaccineService = iVaccineService;
        this.iAnimalVaccineService = iAnimalVaccineService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<Animal> animalPage = this.iAnimalService.cursor(page, pageSize);
        Page<AnimalResponse> animalResponsePage = animalPage
                .map(animal -> this.iModelMapperService.forResponse().map(animal, AnimalResponse.class));
        return ResultHelper.cursor(animalResponsePage);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {

        Animal animal = this.iModelMapperService.forRequest().map(animalSaveRequest, Animal.class);
        Customer customer = this.iCustomerService.get(animalSaveRequest.getCustomerId());
        animal.setCustomer(customer);

        Set<Vaccine> vaccines = new HashSet<>();
        for (Long id : animalSaveRequest.getVaccines()) {
            Vaccine vaccine = this.iVaccineService.get(id);
            vaccines.add(vaccine);
            System.out.println(id);
        }
        Set<Vaccine> newVaccines = new HashSet<>();
        for (Long id : animalSaveRequest.getVaccines()) {
            Vaccine vaccine = this.iVaccineService.get(id);
            newVaccines.add(vaccine);
            System.out.println(id);
        }

        // Validate vaccines
        iAnimalVaccineService.validateAndAddVaccines(newVaccines);
        animal.setVaccines(vaccines);
        this.iAnimalService.save(animal);

        return ResultHelper.created(this.iModelMapperService.forResponse().map(animal, AnimalResponse.class));
    }

    //Filter by name
    @GetMapping("/filterByName")
    public List<Animal> filterByName(@RequestParam String name) {
        return iAnimalService.findByName(name);
    }

    //Filter by customerName
    @GetMapping("/customer")
    public List<Animal> filterByCustomerName(@RequestParam String customerName) {
        return iAnimalService.findByCustomerName(customerName);
    }
    //Filter by customerId
    @GetMapping("/customerId")
    public ResultData<List<AnimalResponse>> filterByCustomeId(@RequestParam Long id) {
        List<Animal> animals = iAnimalService.findAnimalByCustomerId(id);
        if(animals.isEmpty()){
            throw  new NotFoundException("Not Found");
        }
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.iModelMapperService.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(animalResponses);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.iAnimalService.delete(id);
        return ResultHelper.ok();
    }

    //update animal
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        Animal animal = this.iModelMapperService.forRequest().map(animalUpdateRequest, Animal.class);
        Set<Vaccine> vaccines = new HashSet<>();
        for (Long id : animalUpdateRequest.getVaccines()) {
            Vaccine vaccine = this.iVaccineService.get(id);
            vaccines.add(vaccine);
        }
        animal.setVaccines(vaccines);
        this.iAnimalService.update(animal);
        return ResultHelper.success(this.iModelMapperService.forResponse().map(animal, AnimalResponse.class));
    }

       @PostMapping("/{animalId}/vaccines")
       @ResponseStatus(HttpStatus.CREATED)
       public ResponseEntity<ResultData<AnimalResponse>> addVaccineToAnimal(
               @PathVariable Long animalId,
               @RequestBody VacinneAnimalSaveRequest vacinneAnimalSaveRequest) {
           iAnimalVaccineService.addVaccineToAnimal(animalId, vacinneAnimalSaveRequest.getVaccineId());

           Animal updatedAnimal = iAnimalService.get(animalId);

           AnimalResponse animalResponse = iModelMapperService.forResponse().map(updatedAnimal, AnimalResponse.class);
           return ResponseEntity.ok(ResultHelper.success(animalResponse));

       }


}

