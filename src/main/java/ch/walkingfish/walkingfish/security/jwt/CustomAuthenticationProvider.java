package ch.walkingfish.walkingfish.security.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Value("${walkingfish.app.username}")
    private String wk_username;

    @Value("${walkingfish.app.password}")
    private String wk_password;

    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
 
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        if (name.equals(wk_username) && password.equals(wk_password)) {
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        }

        // TODO : return error response, invalid credentials
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
