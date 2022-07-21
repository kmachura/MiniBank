package pl.kmachuramika.minibank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ClientNotHaveSubaccountForThisCurrency extends RuntimeException {

    public ClientNotHaveSubaccountForThisCurrency() {
        super();
    }

    public ClientNotHaveSubaccountForThisCurrency(String message) {
        super(message);
    }
}