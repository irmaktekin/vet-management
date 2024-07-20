package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.IAppointmentService;
import dev.patika.vetmanagement.business.abstracts.ICustomerService;
import dev.patika.vetmanagement.business.abstracts.IDoctorService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.vetmanagement.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.vetmanagement.dto.request.customer.CustomerUpdateRequest;
import dev.patika.vetmanagement.dto.request.doctor.DoctorSaveRequest;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import dev.patika.vetmanagement.dto.response.appointment.AppointmentResponse;
import dev.patika.vetmanagement.dto.response.customer.CustomerSaveResponse;
import dev.patika.vetmanagement.dto.response.doctor.DoctorResponse;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.AvailableDate;
import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/v1/appointments")

public class AppointmentController {
    private final IModelMapperService iModelMapperService;
    private final IAppointmentService iAppointmentService;
    private final IDoctorService iDoctorService;
    private final ICustomerService iCustomerService;

    public AppointmentController(IModelMapperService iModelMapperService, IAppointmentService iAppointmentService, IDoctorService iDoctorService, ICustomerService iCustomerService) {
        this.iModelMapperService = iModelMapperService;
        this.iAppointmentService = iAppointmentService;
        this.iDoctorService = iDoctorService;
        this.iCustomerService = iCustomerService;
    }

    //create a appointment
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest){
        Appointment appointment = this.iModelMapperService.forRequest().map(appointmentSaveRequest,Appointment.class);
        Customer customer = iCustomerService.get(appointmentSaveRequest.getCustomerId());
        Doctor doctor = iDoctorService.get(appointmentSaveRequest.getDoctorId());

        appointment.setCustomer(customer);
        appointment.setDoctor(doctor);
        this.iAppointmentService.save(appointment);

        return ResultHelper.created(this.iModelMapperService.forResponse().map(appointment, AppointmentResponse.class));


    }

    //Get all appointments with pagination
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        Page<Appointment> appointmentPage = this.iAppointmentService.cursor(page,pageSize);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage
                .map(appointment -> this.iModelMapperService.forResponse().map(appointment,AppointmentResponse.class));
        return ResultHelper.cursor(appointmentResponsePage);
    }

    //update appointment
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest){

        // Map the request to the Appointment entity
        Appointment appointment = iModelMapperService.forRequest().map(appointmentUpdateRequest, Appointment.class);
        System.out.println("Updating appointment with ID: " + appointment.getId());

        // Perform the update
        Appointment updatedAppointment = iAppointmentService.update(appointment);

        // Map the updated entity to the response
        AppointmentResponse appointmentResponse = iModelMapperService.forResponse().map(updatedAppointment, AppointmentResponse.class);

        // Return the result
        return ResultHelper.success(appointmentResponse);    }
}
