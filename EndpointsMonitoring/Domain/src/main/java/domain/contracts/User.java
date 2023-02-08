package domain.contracts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //todo: change type to UUID
    private String id;
    private String username;
    private String email;
    @Column(unique=true)
    private String accessToken;

    public User(String username, String email, String accessToken) {
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
    }
}
