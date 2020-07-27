package pl.kmachuramika.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kmachuramika.minibank.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
