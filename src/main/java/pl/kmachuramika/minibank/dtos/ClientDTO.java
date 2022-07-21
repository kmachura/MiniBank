package pl.kmachuramika.minibank.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ClientDTO {

    @JsonIgnore
    private UUID id = UUID.randomUUID();

    private String pesel;

    private String firstName;

    private String surname;

    private String phoneNumber;

    private String email;

    private AccountDTO primaryAccount;

    private List<SubaccountDTO> subAccounts;

}
