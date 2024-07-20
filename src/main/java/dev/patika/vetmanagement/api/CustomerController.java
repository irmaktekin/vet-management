package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.ICustomerService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.vetmanagement.dto.request.customer.CustomerSaveRequest;
import dev.patika.vetmanagement.dto.response.availabledate.AvailableDateResponse;
import dev.patika.vetmanagement.dto.response.customer.CustomerSaveResponse;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")

public class CustomerController {
    private final IModelMapperService iModelMapperService;
    private final ICustomerService iCustomerService;

    public CustomerController(IModelMapperService iModelMapperService, ICustomerService iCustomerService) {
        this.iModelMapperService = iModelMapperService;
        this.iCustomerService = iCustomerService;
    }

    //Add customer
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerSaveResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest){
        Customer customer = this.iModelMapperService.forRequest().map(customerSaveRequest, Customer.class);
        this.iCustomerService.save(customer);
        return ResultHelper.created(this.iModelMapperService.forResponse().map(customer, CustomerSaveResponse.class));


    }

}
