package ch.walkingfish.walkingfish.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import ch.walkingfish.walkingfish.security.services.UserDetailsImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomAuthenticationProvider auth;
  
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
      try {
        String jwt = null; // parseJwt(request);

        final String requestTokenHeader = request.getHeader("Authorization");

        System.out.println("requestTokenHeader recevied : " + requestTokenHeader);

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
          jwt = requestTokenHeader.substring(7);
        }
        else logger.warn("JWT Token does not begin with Bearer String");

        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
          String username = jwtUtils.getUserNameFromJwtToken(jwt);
  
          UserDetails userDetails = UserDetailsImpl.build("username", "Admin$00");
          
          UsernamePasswordAuthenticationToken authentication = 
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
  
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        logger.error("Cannot set user authentication: {}", e);
      }
  
      filterChain.doFilter(request, response);
    }
  
    private String parseJwt(HttpServletRequest request) {
      return jwtUtils.getJwtFromCookies(request);
    }
  }
