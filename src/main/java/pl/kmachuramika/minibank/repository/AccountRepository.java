package pl.kmachuramika.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kmachuramika.minibank.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
