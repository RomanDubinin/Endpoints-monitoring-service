package webservice.infrastructure;

import org.springframework.stereotype.Service;

@Service
public class TokenProvider {
    public TokenProvider() {
        this.token = new ThreadLocal<>();
    }

    private final ThreadLocal<String> token;

    public String getToken() {
        return token.get();
    }

    public void setToken(String tokenString) {
        token.set(tokenString);
    }

    public void cleanToken(){
        this.setToken(null);
    }
}
