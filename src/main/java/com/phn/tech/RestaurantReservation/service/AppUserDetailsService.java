package com.phn.tech.RestaurantReservation.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phn.tech.RestaurantReservation.entity.Users;
import com.phn.tech.RestaurantReservation.exception.UserNotFoundException;
import com.phn.tech.RestaurantReservation.model.CustomerUserDetails;
import com.phn.tech.RestaurantReservation.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	@Primary
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = null;
		try {
			user = usersRepository.findByEmail(username)
			.orElseThrow(() -> new UserNotFoundException());
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User springUser;
		Set<GrantedAuthority> ga = new HashSet<GrantedAuthority>();
		ga.add( new SimpleGrantedAuthority(user.getRole()));
		log.info("Login successful, Role: "+user.getRole());
		springUser = new User(username, user.getPassword(), ga);
		return new CustomerUserDetails(user);

	}

}
