package ch.walkingfish.walkingfish;

// import java.io.IOException;
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.web.filter.OncePerRequestFilter;

// @EnableWebSecurity
// @Configuration
// public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//     @Value("${walkingfish.app.secret_key}")
//     private String secret_key;

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.csrf().disable()
//                 .authorizeRequests()
//                 .antMatchers(HttpMethod.GET, "/api/**").permitAll()
//                 .antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
//                 .antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
//                 .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
//                 .and()
//                 .httpBasic();
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         return new InMemoryUserDetailsManager(
//                 User.builder()
//                         .username("admin")
//                         .password(passwordEncoder().encode(secret_key))
//                         .roles("ADMIN")
//                         .build());
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Override
//     public void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//     }
// }

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
// import ch.walkingfish.walkingfish.security.services.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationProvider authProvider;
    
//   @Autowired
//   UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }
  
  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
      AuthenticationManagerBuilder authenticationManagerBuilder = 
          http.getSharedObject(AuthenticationManagerBuilder.class);
      authenticationManagerBuilder.authenticationProvider(authProvider);
      return authenticationManagerBuilder.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers("/api/auth/**").permitAll()
        .anyRequest().authenticated();
    
    http.authenticationProvider(authProvider);

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }

  // @Bean
  // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  //     http.authorizeRequests()
  //         .antMatchers("/api/auth/**")
  //         .permitAll()
  //         .anyRequest()
  //         .authenticated();
  //         // .and()
  //         // .httpBasic();
  //     return http.build();
  // }
}