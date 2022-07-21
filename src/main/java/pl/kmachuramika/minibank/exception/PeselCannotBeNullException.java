package pl.kmachuramika.minibank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class PeselCannotBeNullException extends RuntimeException {

    public PeselCannotBeNullException() {
        super();
    }

    public PeselCannotBeNullException(String message) {
        super(message);
    }
}