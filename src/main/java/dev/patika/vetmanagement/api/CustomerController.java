package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.ICustomerService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vetmanagement.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.vetmanagement.dto.request.customer.CustomerSaveRequest;
import dev.patika.vetmanagement.dto.request.customer.CustomerUpdateRequest;
import dev.patika.vetmanagement.dto.response.animal.AnimalResponse;
import dev.patika.vetmanagement.dto.response.availabledate.AvailableDateResponse;
import dev.patika.vetmanagement.dto.response.customer.CustomerSaveResponse;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //Filter by name
    @GetMapping("/filterByName")
    public List<Customer> filterByName(@RequestParam String name) {
        return iCustomerService.findByName(name);
    }
    //Delete the customer
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id){
        this.iCustomerService.delete(id);
        return ResultHelper.ok();
    }
    //update customer
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerSaveResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest){
        Customer customer = this.iModelMapperService.forRequest().map(customerUpdateRequest,Customer.class);
        this.iCustomerService.update(customer);
        return ResultHelper.success(this.iModelMapperService.forResponse().map(customer,CustomerSaveResponse.class));
    }

}
