package snttgr.alkemy.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import snttgr.alkemy.challenge.exceptions.InvalidRequestException;

import java.util.NoSuchElementException;


@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public void InvalidRequestException(Exception exception) {
        log.warn(exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void NoSuchElementException(Exception exception){
        log.warn(exception.getMessage());
    }

}
