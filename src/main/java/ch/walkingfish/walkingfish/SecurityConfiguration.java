package ch.walkingfish.walkingfish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ch.walkingfish.walkingfish.security.jwt.AuthEntryPointJwt;
import ch.walkingfish.walkingfish.security.jwt.AuthTokenFilter;
import ch.walkingfish.walkingfish.security.jwt.CustomAuthenticationProvider;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
  @Autowired
  private CustomAuthenticationProvider authProvider;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  /**
   * Create a new AuthTokenFilter
   * 
   * @return the AuthTokenFilter
   */
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  /**
   * Create a new AuthenticationManager
   * 
   * @param http the HttpSecurity
   * @return the AuthenticationManager
   * @throws Exception if an error occurs
   */
  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http
        .getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(authProvider);
    return authenticationManagerBuilder.build();
  }

  /**
   * Create a new PasswordEncoder
   * 
   * @return the PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Create a new SecurityFilterChain
   * 
   * @param http the HttpSecurity
   * @return the SecurityFilterChain
   * @throws Exception if an error occurs
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll().and()
        .authorizeRequests().antMatchers("/api/auth/**").permitAll()
        .anyRequest().authenticated();

    http.authenticationProvider(authProvider);

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}