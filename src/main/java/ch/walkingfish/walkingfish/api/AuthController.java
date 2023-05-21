package ch.walkingfish.walkingfish.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.model.log.LogType;
import ch.walkingfish.walkingfish.model.log.SimpleLog;
import ch.walkingfish.walkingfish.security.jwt.JwtUtils;
import ch.walkingfish.walkingfish.service.ProducerService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController { 
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private ProducerService producerService;

  /**
   * Authenticate a user
   * @param username the username
   * @param password the password
   * @return a JWT token if the authentication is successful
   */
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(String username, String password) {

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateTokenFromUsername(username);

    // Log the login event
    SimpleLog log = new SimpleLog(LogType.INFO, "POST /api/auth/login", "User " + username + " logged in");

    producerService.send(log);

    // TODO : Generate better response, with more details
    return ResponseEntity.ok(jwt);
  }
}
