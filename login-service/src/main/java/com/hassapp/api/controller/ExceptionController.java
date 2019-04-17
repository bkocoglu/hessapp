package com.hassapp.api.controller;

import com.hassapp.api.dto.GeneralResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@PropertySource("classpath:message.properties")
public class ExceptionController extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error(ex.getMessage());
        GeneralResponseDTO response = new GeneralResponseDTO();
        response.setMessage(validationMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            response
        );
    }



    @Value("${exception.validation}")
    private String validationMessage;

}
