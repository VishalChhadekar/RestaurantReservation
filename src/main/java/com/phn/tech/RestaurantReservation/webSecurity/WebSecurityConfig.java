package com.phn.tech.RestaurantReservation.webSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.phn.tech.RestaurantReservation.filter.JWTFilter;
import com.phn.tech.RestaurantReservation.service.AppUserDetailsService;




@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
    private JWTFilter jwtFilter;
	
	@Autowired
	private  AppUserDetailsService appUserDetailsService;

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
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
        .requestMatchers("/customer/**").hasAnyRole("ADMIN","CUSTOMER")
        .requestMatchers(WHITE_LIST_URLS).permitAll()
//        .requestMatchers("/**").permitAll()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
    
	@Bean
	AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConf) throws Exception {
		return authConf.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider
        = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return  provider;

	}
}
