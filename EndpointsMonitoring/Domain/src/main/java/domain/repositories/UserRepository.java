package domain.repositories;

import domain.contracts.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByAccessToken(String accessToken);
}

