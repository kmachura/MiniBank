package pl.kmachuramika.minibank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.kmachuramika.minibank.enums.CurrencyNameEnum;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @EqualsAndHashCode.Exclude
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    private String name;

    @NotNull
    private CurrencyShortcutEnum shortcut;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Rate> rates = new ArrayList<>();

}
