package at.codefabrik.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class HandlerException{
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerException.class);

    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<String> handleInvalidCurrencyException(InvalidCurrencyException ex, WebRequest webRequest){
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpecialCurrencyException.class)
    public ResponseEntity<String> handleSpecialCurrencyException(SpecialCurrencyException ex, WebRequest webRequest){
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
