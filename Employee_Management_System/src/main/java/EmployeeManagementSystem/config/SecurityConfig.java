package EmployeeManagementSystem.config;

import EmployeeManagementSystem.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Is filter ko direct inject karein

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF disable karein kyunki hum stateless token/cookie use kar rahe hain
                .csrf(csrf -> csrf.disable())

                // 2. Apne custom logic ke liye default systems ko band karein
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // 3. Token management ko STATELESS set karein
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. URLs ki Permissions config karein
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/css/**", "/js/**", "/images/**").permitAll() // Sabhi public endpoints khule hain
                        .anyRequest().authenticated() // Dashboard aur baki sab kuch locked rahega
                )

                // 5. Aapka Custom JWT filter lagayein default filter se pehle
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
