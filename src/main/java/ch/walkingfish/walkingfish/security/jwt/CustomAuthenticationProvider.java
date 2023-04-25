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

        System.out.println("Received Name : " + name);
        System.out.println("Received Password : " + password);

        System.out.println("Real Name : " + wk_username);
        System.out.println("Real Password : " + wk_password);
        
        if (name.equals(wk_username) && password.equals(wk_password)) {
            System.out.println("Correct password");
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
