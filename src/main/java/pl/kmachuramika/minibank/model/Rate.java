package pl.kmachuramika.minibank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Rate {

    @EqualsAndHashCode.Exclude
    @Id
    private UUID id = UUID.randomUUID();

    private double midRate;

    private String date;
}
