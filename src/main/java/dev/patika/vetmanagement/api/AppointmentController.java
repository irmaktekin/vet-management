package dev.patika.vetmanagement.api;

import dev.patika.vetmanagement.business.abstracts.IAnimalService;
import dev.patika.vetmanagement.business.abstracts.IAppointmentService;
import dev.patika.vetmanagement.business.abstracts.ICustomerService;
import dev.patika.vetmanagement.business.abstracts.IDoctorService;
import dev.patika.vetmanagement.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.dto.request.appointment.*;
import dev.patika.vetmanagement.dto.response.CursorResponse;
import dev.patika.vetmanagement.dto.response.appointment.AppointmentResponse;
import dev.patika.vetmanagement.entities.Animal;
import dev.patika.vetmanagement.entities.Appointment;
import dev.patika.vetmanagement.entities.Customer;
import dev.patika.vetmanagement.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")

public class AppointmentController {
    private final IModelMapperService iModelMapperService;
    private final IAppointmentService iAppointmentService;
    private final IDoctorService iDoctorService;
    private final ICustomerService iCustomerService;
    private final IAnimalService iAnimalService;

    public AppointmentController(IModelMapperService iModelMapperService, IAppointmentService iAppointmentService, IDoctorService iDoctorService, ICustomerService iCustomerService, IAnimalService iAnimalService) {
        this.iModelMapperService = iModelMapperService;
        this.iAppointmentService = iAppointmentService;
        this.iDoctorService = iDoctorService;
        this.iCustomerService = iCustomerService;
        this.iAnimalService = iAnimalService;
    }

    //create a appointment
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) throws Exception {
        Appointment appointment = this.iModelMapperService.forRequest().map(appointmentSaveRequest,Appointment.class);

        Optional<Animal> optionalAnimal = iAnimalService.findById(appointmentSaveRequest.getAnimalId());
        if (!optionalAnimal.isPresent()) {
            throw new NotFoundException("Animal not found with ID: " + appointmentSaveRequest.getAnimalId());
        }
        Animal animal = optionalAnimal.get();

        // Check if Doctor exists
        Optional<Doctor> optionalDoctor = iDoctorService.findById(appointmentSaveRequest.getDoctorId());
        if (!optionalDoctor.isPresent()) {
            throw new NotFoundException("Doctor not found with ID: " + appointmentSaveRequest.getDoctorId());
        }
        Doctor doctor = optionalDoctor.get();

        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentSaveRequest.getAppointmentDate());
        this.iAppointmentService.save(appointment);

        return ResultHelper.created(this.iModelMapperService.forResponse().map(appointment, AppointmentResponse.class));
    }

    //Get all appointments with pagination
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        Page<Appointment> appointmentPage = this.iAppointmentService.cursor(page,pageSize);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage
                .map(appointment -> this.iModelMapperService.forResponse().map(appointment,AppointmentResponse.class));
        return ResultHelper.cursor(appointmentResponsePage);
    }

    //update appointment
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest){

        Appointment appointment = iModelMapperService.forRequest().map(appointmentUpdateRequest, Appointment.class);
        Doctor doctor = iDoctorService.get(appointmentUpdateRequest.getDoctorId());
        Animal animal = iAnimalService.get(appointmentUpdateRequest.getAnimalId());

        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);

        Appointment updatedAppointment = iAppointmentService.update(appointment);

        AppointmentResponse appointmentResponse = iModelMapperService.forResponse().map(updatedAppointment, AppointmentResponse.class);

        return ResultHelper.success(appointmentResponse);
    }

    //Delete appointment
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id){
        this.iAppointmentService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/filterbyNameAndDate")
    public ResultData<List<AppointmentResponse>> getAppointments(@RequestBody AppointmentFilterRequest appointmentFilterRequest){
        Doctor doctor = iDoctorService.getDoctorByName(appointmentFilterRequest.getDoctorName());

        // Fetch the appointments
        List<Appointment> appointmentList = iAppointmentService.findAppointmentsByDoctorAndDateRange(appointmentFilterRequest.getDoctorName(), appointmentFilterRequest.getStartDate(), appointmentFilterRequest.getEndDate());

        // Map each appointment to AppointmentResponse
        List<AppointmentResponse> appointments = appointmentList.stream()
                .map(appointment -> iModelMapperService.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.successList(appointments);

    }
    //Filter by dates and animal name
    @GetMapping("/filterbyAnimalNameAndDate")
    public ResultData<List<AppointmentResponse>> getAppointmentsByAnimalName(@RequestBody AppointmentFilterByAnimalRequest appointmentFilterByAnimalRequest){
        List <Animal> animal = iAnimalService.findByName(appointmentFilterByAnimalRequest.getAnimalName());
        if (animal.isEmpty()) {
            throw new IllegalArgumentException("Invalid animal name");
        }
        // Fetch the appointments
        List<Appointment> appointmentList = iAppointmentService.findAppointmentsByAnimalAndDateRange(appointmentFilterByAnimalRequest.getAnimalName(), appointmentFilterByAnimalRequest.getStartDate(), appointmentFilterByAnimalRequest.getEndDate());
        System.out.println(appointmentList.size());
        // Map each appointment to AppointmentResponse
        List<AppointmentResponse> appointments = appointmentList.stream()
                .map(appointment -> iModelMapperService.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.successList(appointments);

    }

    //Filterby animal id and dates
    @GetMapping("/filterByAnimalIdAndDate")
    public ResultData<List<AppointmentResponse>> getAppointmentsByAnimalIdAndDate(
            @RequestBody FilterRequestByAnımalId filterRequestByAnımalId  ) {

        // Check if the animal exists
        Animal animal = iAnimalService.get(filterRequestByAnımalId.getAnimalId());
        if (animal == null) {
            throw new NotFoundException("No animal found");
        }

        // Fetch the appointments
        List<Appointment> appointmentList = iAppointmentService.findAppointmentsByAnimalIdAndDateRange(animal.getId(),filterRequestByAnımalId.getStartDate(), filterRequestByAnımalId.getEndDate());

        // Map each appointment to AppointmentResponse
        List<AppointmentResponse> appointments = appointmentList.stream()
                .map(appointment -> iModelMapperService.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.successList(appointments);
    }

    //filter by doctor Id and date
    @GetMapping("/filterByDoctorIdAndDate")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDate(@RequestBody FilterRequestByDoctorId filterRequest) {
        List<Appointment> appointments = iAppointmentService.getAppointmentsByDoctorAndDateRange(
                filterRequest.getDoctorId(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate()
        );

        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(appointment -> iModelMapperService.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.successList(appointmentResponses);
    }
}
