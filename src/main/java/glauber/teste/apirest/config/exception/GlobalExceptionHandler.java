package glauber.teste.apirest.config.exception;

import glauber.teste.apirest.dto.SimpleError;
import glauber.teste.apirest.exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@ComponentScan({"glauber.teste.apirest"})
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpleErrorTranslator errorTranslator;

    @ExceptionHandler
    public ResponseEntity<SimpleError> handleException(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException e = (HttpException) throwable;
            SimpleError error = translate(e.getError());
            this.log.error(error.getMessage(), e.getCause() != null ? e.getCause() : e);
            return ResponseEntity.status(e.getHttpStatus()).body(error);
        }
        this.log.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler
    public ResponseEntity<SimpleError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        this.log.error(e.getMessage(), e);
        SimpleError error = new SimpleError();
        error.setMessage("error.argumentNotValid");

        if (e.getBindingResult() != null && e.getBindingResult().getAllErrors() != null) {
            e.getBindingResult().getAllErrors().forEach(erro -> error.addDetail(erro.getDefaultMessage()));
        } else {
            error.addDetail(e.getMessage());
        }
        return new ResponseEntity<>(translate(error), HttpStatus.BAD_REQUEST);
    }

    private SimpleError translate(SimpleError error) {
        return errorTranslator.translate(error);
    }
}
