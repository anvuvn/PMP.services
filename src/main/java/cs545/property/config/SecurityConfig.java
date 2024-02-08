package cs545.property.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true,
        securedEnabled = true
)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             PasswordEncoder bCryptPasswordEncoder,
                                             UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var roles = List.of("Admin", "User");
        http
                .cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {

                    }
                })
                .csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
                        httpSecurityCsrfConfigurer.disable();
                    }
                })

                .authorizeRequests()
                .requestMatchers("/authenticate/**").permitAll()
                .requestMatchers("/favourites").authenticated()
                .requestMatchers("/favourites/**").authenticated()
                .requestMatchers("/offers").authenticated()
                .requestMatchers("/offers/**").authenticated()
//                .requestMatchers("/properties/*/offers").authenticated()
                .requestMatchers(HttpMethod.POST, "/properties").authenticated() //.permitAll()
                .requestMatchers("/properties").permitAll()
                .requestMatchers("/properties/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/images").authenticated()
                .requestMatchers("/images").permitAll()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/messages").authenticated()
                .requestMatchers("/messages/**").authenticated()
                .requestMatchers("/admin/**").hasAnyAuthority("Admin")
                .requestMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement(new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(SessionManagementConfigurer<HttpSecurity> httpSecuritySessionManagementConfigurer) {
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    }
                });

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}