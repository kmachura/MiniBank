package pl.kmachuramika.minibank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.kmachuramika.minibank.enums.AccountTypeEnum;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@SequenceGenerator(name = "GEN_ID_AN", sequenceName = "SEQ_ACNUMBER", allocationSize = 1)
@Entity
public class Account {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ID_AN")
    private Long id;

    @NotNull
    Long accountNumber;

    @NotNull
    private AccountTypeEnum accountType;

    @NotNull
    private BigDecimal accountBalance;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    private Currency currency;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Client client;

}
