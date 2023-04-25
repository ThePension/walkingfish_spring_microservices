package ch.walkingfish.walkingfish.security.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
  
    private String username;
    
    @JsonIgnore
    private String password;
    
    public UserDetailsImpl(String username, String password) {
      this.username = username;
      this.password = password;
    }
  
    public static UserDetailsImpl build(String username, String password) {
      return new UserDetailsImpl(
          username,
          password);
    }
  
    @Override
    public String getPassword() {
      return password;
    }
  
    @Override
    public String getUsername() {
      return username;
    }
  
    @Override
    public boolean isAccountNonExpired() {
      return true;
    }
  
    @Override
    public boolean isAccountNonLocked() {
      return true;
    }
  
    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }
  
    @Override
    public boolean isEnabled() {
      return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a collection of SimpleGrantedAuthority objects containing admin role
        return Collections.singletonList(new SimpleGrantedAuthority("admin"));
    }
  }
