package dev.patika.vetmanagement.api;


import dev.patika.vetmanagement.business.abstracts.IAnimalVaccineService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.vaccineAnimal.VacinneAnimalDateRangeRequest;
import dev.patika.vetmanagement.dto.response.AnimalVaccineResponse;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import dev.patika.vetmanagement.entities.AnimalVaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/animalvaccines")
public class AnimalVaccineController {
    private final IAnimalVaccineService iAnimalVaccineService;
    private final IModelMapperService iModelMapperService;

    public AnimalVaccineController(IAnimalVaccineService iAnimalVaccineService, IModelMapperService iModelMapperService) {
        this.iAnimalVaccineService = iAnimalVaccineService;
        this.iModelMapperService = iModelMapperService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalVaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestBody VacinneAnimalDateRangeRequest vacinneAnimalDateRangeRequest
            ) {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<AnimalVaccine> animalVaccinePage = iAnimalVaccineService.getAnimalVaccinesByProtectionFinishDateBetween(
                    vacinneAnimalDateRangeRequest.getStartDate(), vacinneAnimalDateRangeRequest.getEndDate(), pageable);
            Page<AnimalVaccineResponse> animalVaccineResponsePage = animalVaccinePage
                    .map(animalVaccine -> iModelMapperService.forResponse().map(animalVaccine, AnimalVaccineResponse.class));
            return ResultHelper.cursor(animalVaccineResponsePage);
    }

}
