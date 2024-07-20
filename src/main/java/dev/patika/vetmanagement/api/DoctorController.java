package dev.patika.vetmanagement.api;
import dev.patika.vetmanagement.business.abstracts.IAvailableDateService;
import dev.patika.vetmanagement.business.abstracts.IDoctorService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.doctor.DoctorSaveRequest;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import dev.patika.vetmanagement.dto.response.doctor.DoctorResponse;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final IDoctorService iDoctorService;
    private final IModelMapperService iModelMapperService;
    private final IAvailableDateService availableDateService;

    public DoctorController(IDoctorService iDoctorService, IModelMapperService iModelMapperService, IAvailableDateService availableDateService) {
        this.iDoctorService = iDoctorService;
        this.iModelMapperService = iModelMapperService;
        this.availableDateService = availableDateService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest){
        Doctor doctor = this.iModelMapperService.forRequest().map(doctorSaveRequest,Doctor.class);
        Set<AvailableDate> availableDates = new HashSet<>();
        System.out.println(doctorSaveRequest.getAvailableDateIds());
        for (Long id : doctorSaveRequest.getAvailableDateIds()) {
            AvailableDate availableDate = this.availableDateService.get(id);
            availableDates.add(availableDate);
        }
        doctor.setAvailableDates(availableDates);
        this.iDoctorService.save(doctor);

        return ResultHelper.created(this.iModelMapperService.forResponse().map(doctor, DoctorResponse.class));


    }

    //Get all doctors with pagination
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        Page<Doctor> categoryPage = this.iDoctorService.cursor(page,pageSize);
        Page<DoctorResponse> categoryResponsePage = categoryPage
                .map(category -> this.iModelMapperService.forResponse().map(category,DoctorResponse.class));
        return ResultHelper.cursor(categoryResponsePage);
    }
    //Get By Id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("id") int id){
        Doctor doctor = this.iDoctorService.get(id);
        DoctorResponse doctorResponse = this.iModelMapperService.forResponse().map(doctor,DoctorResponse.class);
        return ResultHelper.success(doctorResponse);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id){
        this.iDoctorService.delete(id);
        return ResultHelper.ok();
    }

}
