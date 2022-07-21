package pl.kmachuramika.minibank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class TooYoungException extends RuntimeException {

    public TooYoungException() {
        super();
    }

    public TooYoungException(String message) {
        super(message);
    }
}