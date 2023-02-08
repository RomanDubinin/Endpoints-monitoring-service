package webservice.infrastructure;

// Probably there should be some builtin runtime exception for such scenarios,
// but I didn't find anything suitable...
public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
