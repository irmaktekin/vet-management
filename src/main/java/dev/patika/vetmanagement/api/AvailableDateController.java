package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.IAvailableDateService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vetmanagement.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.vetmanagement.dto.request.availabledate.AvailableDateUpdateRequest;
import dev.patika.vetmanagement.dto.request.doctor.DoctorSaveRequest;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import dev.patika.vetmanagement.dto.response.animal.AnimalResponse;
import dev.patika.vetmanagement.dto.response.availabledate.AvailableDateResponse;
import dev.patika.vetmanagement.dto.response.doctor.DoctorResponse;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
@RestController
@RequestMapping("/v1/availabledates")

public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IModelMapperService iModelMapperService;

    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService iModelMapperService) {
        this.availableDateService = availableDateService;
        this.iModelMapperService = iModelMapperService;
    }

    //Add available dates
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest){
        AvailableDate availableDate = this.iModelMapperService.forRequest().map(availableDateSaveRequest,AvailableDate.class);
        this.availableDateService.save(availableDate);
        return ResultHelper.created(this.iModelMapperService.forResponse().map(availableDate, AvailableDateResponse.class));


    }

    //Update available date information
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        AvailableDate availableDate = this.iModelMapperService.forRequest().map(availableDateUpdateRequest, AvailableDate.class);
        this.availableDateService.update(availableDate);
        return ResultHelper.success(this.iModelMapperService.forResponse().map(availableDate, AvailableDateResponse.class));
    }
    //Delete available date
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.availableDateService.delete(id);
        return ResultHelper.ok();
    }

    //Get all available dates which can added to doctor
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        Page<AvailableDate> availableDatePage = this.availableDateService.cursor(page,pageSize);
        Page<AvailableDateResponse> availableDates = availableDatePage
                .map(availableDate -> this.iModelMapperService.forResponse().map(availableDate,AvailableDateResponse.class));
        return ResultHelper.cursor(availableDates);
    }
}
