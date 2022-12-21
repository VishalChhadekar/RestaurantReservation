package com.phn.tech.RestaurantReservation.webSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private static final String[] WHITE_LIST_URLS ={
			"/",
		"/register/**",
		"/login/**"
	};
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder(11);
	}

    @Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.cors()
		.and()
		.csrf()
		.disable()
		.authorizeHttpRequests()
        .requestMatchers("/admin/**").hasRole("admin")
        .requestMatchers("/manager/**").hasAnyRole("admin", "manager")
        .requestMatchers("/customer/**").hasAnyRole("admin","customer")
        .requestMatchers(WHITE_LIST_URLS).permitAll();
		return http.build();
	}
    
	@Bean
	AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConf) throws Exception {
		return authConf.getAuthenticationManager();
	}

}
