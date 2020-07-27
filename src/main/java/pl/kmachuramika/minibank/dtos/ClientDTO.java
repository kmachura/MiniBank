package pl.kmachuramika.minibank.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ClientDTO {

    private String pesel;

    private String firstName;

    private String surname;

    private String phoneNumber;

    private String email;

    private AccountDTO primaryAccount;

    private List<SubaccountDTO> subAccounts;

}
