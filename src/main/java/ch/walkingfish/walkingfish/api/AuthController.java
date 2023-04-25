package ch.walkingfish.walkingfish.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.security.jwt.JwtUtils;
import ch.walkingfish.walkingfish.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController { 
    @Autowired
    AuthenticationManager authenticationManager;
     
    @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(String username, String password) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateTokenFromUsername(username);

    // UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    
    // List<String> roles = userDetails.getAuthorities().stream()
    //     .map(item -> item.getAuthority())
    //     .collect(Collectors.toList());

    return ResponseEntity.ok(jwt);
  }
}
