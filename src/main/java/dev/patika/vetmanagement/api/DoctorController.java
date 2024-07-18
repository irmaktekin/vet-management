package dev.patika.vetmanagament.api;

import dev.patika.vetmanagament.business.abstracts.IDoctorService;
import dev.patika.vetmanagament.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagament.core.result.Result;
import dev.patika.vetmanagament.core.result.ResultData;
import dev.patika.vetmanagament.core.utilities.ResultHelper;
import dev.patika.vetmanagament.dto.request.doctor.DoctorSaveRequest;
import dev.patika.vetmanagament.dto.response.CursorResponse;
import dev.patika.vetmanagament.dto.response.DoctorResponse;
import dev.patika.vetmanagament.entities.AvailableDate;
import dev.patika.vetmanagament.entities.Doctor;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final IDoctorService iDoctorService;
    private final IModelMapperService iModelMapperService;

    public DoctorController(IDoctorService iDoctorService, IModelMapperService iModelMapperService) {
        this.iDoctorService = iDoctorService;
        this.iModelMapperService = iModelMapperService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest){
        Doctor doctor = this.iModelMapperService.forRequest().map(doctorSaveRequest,Doctor.class);
        this.iDoctorService.save(doctor);
        return ResultHelper.created(this.iModelMapperService.forResponse().map(doctor,DoctorResponse.class));
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
