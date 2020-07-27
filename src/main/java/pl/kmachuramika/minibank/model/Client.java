package pl.kmachuramika.minibank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@SequenceGenerator(name = "GEN_ID_CL", sequenceName = "SEQ_CLIENT", allocationSize = 1)
@Entity
public class Client {

    @EqualsAndHashCode.Exclude
    @Id
    @NotNull
    @PESEL
    private String pesel;

    @NotNull
    private String firstName;

    @NotNull
    private String surname;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Email
    private String email;

    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Account primaryAccount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Account> subAccounts;

}

