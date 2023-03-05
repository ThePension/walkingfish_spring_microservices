package ch.walkingfish.walkingfish;

import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

@EnableWebSecurity
// @Profile(value = "secure")
public class SecurityConfiguration {
    // @Value("${auth0.audience}")
    // private String audience;

    // @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    // private String issuer;

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    // @Profile(value = "secure")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.logout()
                .logoutSuccessUrl("/catalogue");

        // Disable crsf
        http.cors().and().csrf().disable();

        // http.authorizeRequests()
        // .antMatchers(
        // "/",
        // "/accueil",
        // "/catalogue/**",
        // "/api/**",
        // "/articlesImages/**",
        // "/css/**",
        // "/img/**",
        // "/js/**",
        // "/h2/**",
        // "/fonts/**")
        // .permitAll()
        // .anyRequest().authenticated()
        // .and().formLogin().permitAll();

        return http.build();

        // http.cors()
        // .and()
        // .authorizeRequests()
        // .antMatchers(HttpMethod.GET, "/api/**")
        // .hasAuthority("SCOPE_read")
        // .antMatchers(HttpMethod.POST, "/api/**")
        // .hasAuthority("SCOPE_write")
        // .anyRequest()
        // .authenticated()
        // .and()
        // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        // return http.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // JwtDecoder jwtDecoder() {
    // OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(audience);
    // OAuth2TokenValidator<Jwt> withIssuer =
    // JwtValidators.createDefaultWithIssuer(issuer);
    // OAuth2TokenValidator<Jwt> validator = new
    // DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);

    // NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
    // JwtDecoders.fromOidcIssuerLocation(issuer);
    // jwtDecoder.setJwtValidator(validator);
    // return jwtDecoder;
    // }

    // @Bean
    // public JwtDecoderFactory<ClientRegistration> jwtDecoderFactory() {

    // final JwtDecoder decoder = new JwtDecoder() {

    // @SneakyThrows
    // @Override
    // public Jwt decode(String token) throws JwtException {
    // JWT jwt = JWTParser.parse(token);
    // return createJwt(token, jwt);
    // }

    // private Jwt createJwt(String token, JWT parsedJwt) {
    // try {
    // Map<String, Object> headers = new
    // LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
    // Map<String, Object> claims = parsedJwt.getJWTClaimsSet().getClaims();
    // return Jwt.withTokenValue(token)
    // .headers(h -> h.putAll(headers))
    // .claims(c -> c.putAll(claims))
    // .build();
    // } catch (Exception ex) {
    // if (ex.getCause() instanceof ParseException) {
    // throw new JwtException(String.format(DECODING_ERROR_MESSAGE_TEMPLATE,
    // "Malformed payload"));
    // } else {
    // throw new JwtException(String.format(DECODING_ERROR_MESSAGE_TEMPLATE,
    // ex.getMessage()), ex);
    // }
    // }
    // }
    // };
    // return context -> decoder;
    // }
}