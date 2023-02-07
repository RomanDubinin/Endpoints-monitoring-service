package webservice.infrastructure;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {
    private final TokenProvider tokenProvider;

    public AuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            var httpRequest = (HttpServletRequest) request;
            var token = httpRequest.getHeader("accessToken");
            tokenProvider.setToken(token);
            chain.doFilter(request, response);
        } finally {
            tokenProvider.cleanToken();
        }
    }
}
