package glauber.teste.apirest.exception;

import org.springframework.http.HttpStatus;

public class ContaNotFoundException extends HttpException {

    public ContaNotFoundException() {
        super("error.contaNotFound");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
