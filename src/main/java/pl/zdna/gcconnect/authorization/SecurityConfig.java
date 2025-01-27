package pl.zdna.gcconnect.authorization;

import lombok.extern.log4j.Log4j2;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final HttpSecurity httpSecurity =
                http.authorizeHttpRequests(
                                authorize ->
                                        authorize
                                                .requestMatchers("/")
                                                .permitAll()
                                                .requestMatchers("/hello")
                                                .permitAll()
                                                .requestMatchers("/login")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                        //                .oauth2ResourceServer((oauth2) ->
                        // oauth2.jwt(Customizer.withDefaults()));
                        //                .oauth2Login(login ->
                        // login.loginPage("/login").permitAll());
                        .oauth2Login(oauth2 -> oauth2.successHandler(handler()));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
        return JwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri());
    }

    @Bean
    public AuthenticationSuccessHandler handler() {
        return new CustomAuthenticationSuccessHandler();
    }
}
