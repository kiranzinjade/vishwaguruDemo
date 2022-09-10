package com.techvg.vks.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<CustomErrors> handleGenericNotFoundException(NotFoundException e,
                                                                       HttpServletRequest request) {
        CustomErrors error = CustomErrors.builder()
                                .errorCode("NOT_FOUND_ERROR")
                                .errorMessage(e.getMessage())
                                .requestedURI(request.getRequestURI())
                                .timestamp(LocalDateTime.now())
                                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AlreadyExitsException.class)
    public ResponseEntity<CustomErrors> handleGenericAlreadyExitsException(AlreadyExitsException e,
                                                                       HttpServletRequest request) {
        CustomErrors error = CustomErrors.builder()
                .errorCode("ALREADY_EXISTS_ERROR")
                .errorMessage(e.getMessage())
                .requestedURI(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> constraintViolationException(ConstraintViolationException ex) {
        List<String> errorList = new ArrayList<>(ex.getConstraintViolations().size());
        ex.getConstraintViolations().forEach(error -> errorList.add(error.toString()));
        return new ResponseEntity<>(errorList,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrors>  handleSQLException( DataIntegrityViolationException e,
                                                             HttpServletRequest request) {

        CustomErrors error = CustomErrors.builder()
                .errorCode("409")
                .errorMessage(e.getCause().getCause().getMessage()!=null?e.getCause().getCause().getMessage():e.getMessage()) // TO get any SQL Exception error details
                .requestedURI(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
   @ExceptionHandler(Exception.class)
   public ResponseEntity<CustomErrors>  handleException( Exception e,
                                                          HttpServletRequest request) {
            CustomErrors error = CustomErrors.builder()
                    .errorCode("INTERNAL_SERVER_ERROR")
                    .errorMessage(e.getMessage())
                    .requestedURI(request.getRequestURI())
                    .timestamp(LocalDateTime.now())
                    .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
