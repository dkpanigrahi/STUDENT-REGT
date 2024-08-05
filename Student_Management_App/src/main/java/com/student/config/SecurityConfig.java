package com.student.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.student.security.JwtAuthenticationEntryPoint;
import com.student.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
    private JwtAuthenticationEntryPoint point;
    
	@Autowired
    private JwtAuthenticationFilter filter;
	
	/*
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 * throws Exception {
	 * 
	 * http.csrf(csrf -> csrf.disable()) .cors(cors -> cors.disable())
	 * .authorizeHttpRequests(auth ->
	 * auth.requestMatchers("/students/**").authenticated()
	 * .requestMatchers("/auth/login").permitAll() .anyRequest().authenticated())
	 * .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
	 * .sessionManagement(session ->
	 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) ;
	 * http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	 * return http.build(); }
	 */
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
                .requestMatchers("/students/**").authenticated()
                .requestMatchers("/auth/login").permitAll()
                .anyRequest().authenticated())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        http.headers(headers -> headers.frameOptions().disable()); // Allow frames for H2 console
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
