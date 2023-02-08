package webservice.services;

import domain.contracts.User;
import domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import webservice.infrastructure.AuthorizationException;
import webservice.infrastructure.TokenProvider;

@Service
public class UserService {
    private UserRepository userRepository;

    private TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public User getCurrentlyAuthorisedUser() {
        var token = tokenProvider.getToken();
        return userRepository.findByAccessToken(token).orElseThrow(() -> new AuthorizationException("User is not authorised"));
    }
}
