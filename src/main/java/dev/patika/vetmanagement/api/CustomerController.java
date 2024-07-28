package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.ICustomerService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vetmanagement.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.vetmanagement.dto.request.customer.CustomerFilterRequest;
import dev.patika.vetmanagement.dto.request.customer.CustomerSaveRequest;
import dev.patika.vetmanagement.dto.request.customer.CustomerUpdateRequest;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import dev.patika.vetmanagement.dto.response.animal.AnimalResponse;
import dev.patika.vetmanagement.dto.response.availabledate.AvailableDateResponse;
import dev.patika.vetmanagement.dto.response.customer.CustomerSaveResponse;
import dev.patika.vetmanagement.dto.response.doctor.DoctorResponse;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResultData<List<CustomerSaveResponse>> filterByName(@Valid @RequestBody CustomerFilterRequest customerFilterRequest) {
        Customer customerCriteria = this.iModelMapperService.forRequest().map(customerFilterRequest, Customer.class);
        List<Customer> customers = this.iCustomerService.findByName(customerCriteria.getName());
        List<CustomerSaveResponse> customerResponses = customers.stream()
                .map(customer -> this.iModelMapperService.forResponse().map(customer, CustomerSaveResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(customerResponses);
    }
    //get all customers
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerSaveResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){

        Page<Customer> customerPage = this.iCustomerService.cursor(page,pageSize);
        Page<CustomerSaveResponse> customerSaveResponsePage = customerPage
                .map(customer -> this.iModelMapperService.forResponse().map(customer,CustomerSaveResponse.class));
        return ResultHelper.cursor(customerSaveResponsePage);
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
