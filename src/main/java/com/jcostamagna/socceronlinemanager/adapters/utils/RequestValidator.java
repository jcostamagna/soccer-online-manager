package com.jcostamagna.socceronlinemanager.adapters.utils;

import com.jcostamagna.socceronlinemanager.adapters.controller.exceptions.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class RequestValidator {

    public void validateRequest(Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            throw new BadRequestException(message);
        }
    }
}
