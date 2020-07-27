package pl.kmachuramika.minibank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Data
@SequenceGenerator(name = "GEN_ID_CUR", sequenceName = "SEQ_CURRENCY", allocationSize = 1)
@Entity
public class Currency {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ID_CUR")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String shortcut;

    private Rate[] rates;

}
