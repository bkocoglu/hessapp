package com.hassapp.api.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@PropertySource("classpath:message.properties")
public class DatabaseException extends RuntimeException {

    @Value("${exception.database.undefined}")
    private static String message;

    public DatabaseException(){
        super(message);
    }

}
