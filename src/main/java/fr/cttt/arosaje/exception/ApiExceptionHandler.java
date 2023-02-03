package fr.cttt.arosaje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ElementNotFoundException.class})
    public ResponseEntity<Object> handleElementNotFoundException(ElementNotFoundException e){
        ApiExceptionWrapper apiExceptionWrapper = new ApiExceptionWrapper(
                e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionWrapper, HttpStatus.NOT_FOUND);
    }
}
