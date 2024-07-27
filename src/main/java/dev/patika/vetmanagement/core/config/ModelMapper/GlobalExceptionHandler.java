package dev.patika.vetmanagement.core.config.ModelMapper;

import com.fasterxml.jackson.databind.util.ExceptionUtil;
import dev.patika.vetmanagement.core.exception.AvailableDateNotFoundException;
import dev.patika.vetmanagement.core.exception.DuplicateRecordException;
import dev.patika.vetmanagement.core.exception.InvalidGenderException;
import dev.patika.vetmanagement.core.exception.NotFoundException;
import dev.patika.vetmanagement.core.result.Result;
import dev.patika.vetmanagement.core.result.ResultData;
import dev.patika.vetmanagement.core.utilities.Message;
import dev.patika.vetmanagement.core.utilities.ResultHelper;
import dev.patika.vetmanagement.entities.Gender;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> handleNotFoundException(NotFoundException e){
        return new ResponseEntity<>(ResultHelper.notFoundError(e.getMessage()),HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleErrors(MethodArgumentNotValidException e) {
        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(ResultHelper.validateError(e.getMessage()),HttpStatus.BAD_REQUEST);

    }
   @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> validationErrorList = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return new ResponseEntity<>(ResultHelper.duplicateRecord(validationErrorList), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ResultData<String>> handleDuplicateRecordException(DuplicateRecordException ex) {
        String rootCauseMessage = ex.getMessage();
        List<String> validationErrorList = Collections.singletonList(rootCauseMessage);
        ResultData<String> result = ResultHelper.duplicateRecord("Duplicate values: " + ex.getFieldName());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AvailableDateNotFoundException.class)
    public ResponseEntity<Object> handleAvailableDateNotFound(AvailableDateNotFoundException e){
        return new ResponseEntity<>(ResultHelper.notFoundError(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidGenderException.class)
    public ResponseEntity<Object> handleInvalidGenderException(InvalidGenderException ex) {
        // Customize the response body to include only the status and message
        return new ResponseEntity<>(ResultHelper.invalidEnum(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
