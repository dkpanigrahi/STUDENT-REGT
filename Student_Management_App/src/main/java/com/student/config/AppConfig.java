package com.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    public UserDetailsService userDetailsService() {
		
    	UserDetails user = User.builder().username("student").password(passwordEncoder().encode("password")).roles("STUDENT").build();
    	UserDetails user2 = User.builder().username("admin").password(passwordEncoder().encode("password")).roles("ADMIN").build();
    	
    	return new InMemoryUserDetailsManager(user,user2);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

	
	/*
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 * throws Exception { http.csrf().disable() .authorizeRequests()
	 * .requestMatchers("/students/**").hasRole("STUDENT")
	 * .requestMatchers("/subjects/**").hasRole("ADMIN")
	 * .anyRequest().authenticated() .and() .formLogin().permitAll() .and()
	 * .logout().permitAll(); return http.build(); }
	 */
	 
}
