package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.IAnimalService;
import dev.patika.vetmanagement.business.abstracts.IVaccineService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.exception.AnimalNotFoundException;
import dev.patika.vetmanagement.core.exception.VaccineNotFoundException;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.customer.CustomerUpdateRequest;
import dev.patika.vetmanagement.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vetmanagement.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import dev.patika.vetmanagement.dto.response.customer.CustomerSaveResponse;
import dev.patika.vetmanagement.dto.response.doctor.DoctorResponse;
import dev.patika.vetmanagement.dto.response.vaccine.VaccineResponse;
import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Doctor;
import dev.patika.vetmanagement.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IModelMapperService iModelMapperService;
    private final IVaccineService iVaccineService;
    private final IAnimalService iAnimalService;

    public VaccineController(IModelMapperService iModelMapperService, IVaccineService iVaccineService, IAnimalService iAnimalService) {
        this.iModelMapperService = iModelMapperService;
        this.iVaccineService = iVaccineService;
        this.iAnimalService = iAnimalService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest){
        Vaccine vaccine = this.iModelMapperService.forRequest().map(vaccineSaveRequest, Vaccine.class);
        this.iVaccineService.save(vaccine);
        return ResultHelper.created(this.iModelMapperService.forResponse().map(vaccine, VaccineResponse.class));


    }
    //Filter by animalId
    //Get By Id
    @GetMapping("/animal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> get(@PathVariable("id") int animalId){
        // Fetch vaccines by animal ID

        List<Vaccine> vaccines = iVaccineService.findByAnimalId(animalId);

        // Initialize list for VaccineResponse
        List<VaccineResponse> vaccineResponses = new ArrayList<>();

        // Map each Vaccine to VaccineResponse individually
        for (Vaccine vaccine : vaccines) {
            VaccineResponse response = iModelMapperService.forResponse().map(vaccine, VaccineResponse.class);
            vaccineResponses.add(response);
        }

        // Return the list of VaccineResponse
        return ResultHelper.successList(vaccineResponses);

    }

    //update vaccine
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest){

        Vaccine vaccine = this.iModelMapperService.forRequest().map(vaccineUpdateRequest,Vaccine.class);
        this.iVaccineService.update(vaccine);
        return ResultHelper.success(this.iModelMapperService.forResponse().map(vaccine,VaccineResponse.class));
    }

    //Get all vaccines with pagination
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        Page<Vaccine> vaccinePage = this.iVaccineService.cursor(page,pageSize);
        Page<VaccineResponse> vaccineResponsePage = vaccinePage
                .map(vaccine -> this.iModelMapperService.forResponse().map(vaccine,VaccineResponse.class));
        return ResultHelper.cursor(vaccineResponsePage);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id){
        this.iVaccineService.delete(id);
        return ResultHelper.ok();
    }
}
