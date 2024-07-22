package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.*;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
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
import dev.patika.vetmanagement.dto.response.doctor.DoctorResponse;
import dev.patika.vetmanagement.entities.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService iAnimalService;
    private final IModelMapperService iModelMapperService;
    private final IDoctorService iDoctorService;
    private final ICustomerService iCustomerService;
    private final IVaccineService iVaccineService;
    private final IAnimalVaccineService iAnimalVaccineService;

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
        Doctor doctor = this.iDoctorService.get(animalSaveRequest.getDoctorId());
        Customer customer = this.iCustomerService.get(animalSaveRequest.getCustomerId());
        animal.setDoctor(doctor);
        animal.setCustomer(customer);

        Set<Vaccine> vaccines = new HashSet<>();
        for (Long id : animalSaveRequest.getVaccines()) {
            Vaccine vaccine = this.iVaccineService.get(id);
            vaccines.add(vaccine);
        }
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
        this.iAnimalService.update(animal);
        return ResultHelper.success(this.iModelMapperService.forResponse().map(animal, AnimalResponse.class));
    }

   /* @PostMapping("/{animalId}/vaccines")
    public ResponseEntity<String> addVaccineToAnimal(
            @PathVariable Long animalId,
            @RequestParam String code,
            @RequestParam String name) {
        try {
            iAnimalVaccineService.getActiveVaccine(animalId, code,name);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vaccine added to animal successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/


       @PostMapping("/{animalId}/vaccines")
       @ResponseStatus(HttpStatus.CREATED)
       public ResponseEntity<String> addVaccineToAnimal(
               @PathVariable Long animalId,
               @RequestBody VacinneAnimalSaveRequest vacinneAnimalSaveRequest)
               {
           try {
               iAnimalVaccineService.addVaccineToAnimal(animalId, vacinneAnimalSaveRequest.getVaccineCode(), vacinneAnimalSaveRequest.getVaccineName());
               return ResponseEntity.status(HttpStatus.CREATED).body("Vaccine added to animal successfully.");
           } catch (IllegalStateException e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
           } catch (RuntimeException e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
           }
       }

}
