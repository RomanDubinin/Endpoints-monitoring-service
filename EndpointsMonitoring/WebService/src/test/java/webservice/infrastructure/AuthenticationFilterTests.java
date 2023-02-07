package webservice.infrastructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationFilterTests {
    @Mock
    private HttpServletRequest request;
    @Mock
    private FilterChain chain;
    @Mock
    private TokenProvider tokenProvider;

    @Test
    public void authFilter_tokenProvided_tokenSetToTokenProviderAndNextInChainCalledAndTokenCleaned() throws ServletException, IOException {
        when(request.getHeader("accessToken")).thenReturn("token");
        var filter = new AuthenticationFilter(tokenProvider);

        filter.doFilter(request, null, chain);

        verify(tokenProvider).setToken("token");
        verify(chain).doFilter(request, null);
        verify(tokenProvider).cleanToken();
    }

    @Test
    public void authFilter_exceptionFromNextInChainThrown_TokenCleaned() throws ServletException, IOException {
        when(request.getHeader("accessToken")).thenReturn("token");
        doThrow(ServletException.class).when(chain).doFilter(request, null);
        var filter = new AuthenticationFilter(tokenProvider);

        assertThrows(ServletException.class, () -> {
            filter.doFilter(request, null, chain);
        });
        verify(tokenProvider).cleanToken();
    }
}
