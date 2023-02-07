package domain.repositories;

import domain.contracts.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, String> {
}

