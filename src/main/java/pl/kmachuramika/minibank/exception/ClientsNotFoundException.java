package pl.kmachuramika.minibank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientsNotFoundException extends RuntimeException {

    public ClientsNotFoundException() {
        super();
    }

    public ClientsNotFoundException(String message) {
        super(message);
    }
}