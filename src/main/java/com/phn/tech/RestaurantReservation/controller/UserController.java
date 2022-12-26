package com.phn.tech.RestaurantReservation.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.phn.tech.RestaurantReservation.entity.Reservation;
import com.phn.tech.RestaurantReservation.entity.Restaurant;
import com.phn.tech.RestaurantReservation.entity.RestaurantTable;
import com.phn.tech.RestaurantReservation.exception.UserNotFoundException;
import com.phn.tech.RestaurantReservation.model.AddMoneyToWallet;
import com.phn.tech.RestaurantReservation.model.CustomerWalletResponse;
import com.phn.tech.RestaurantReservation.model.JWTRequest;
import com.phn.tech.RestaurantReservation.model.JWTResponse;
import com.phn.tech.RestaurantReservation.model.MakePayment;
import com.phn.tech.RestaurantReservation.model.RestaurantModel;
import com.phn.tech.RestaurantReservation.model.UserModel;
import com.phn.tech.RestaurantReservation.service.AppUserDetailsService;
import com.phn.tech.RestaurantReservation.service.UserService;
import com.phn.tech.RestaurantReservation.service.WalletService;
import com.phn.tech.RestaurantReservation.utility.JWTUtility;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	
	
	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//Registration
	@PostMapping("/register")
	public ResponseEntity<Long> registerUser(@Valid @RequestBody UserModel userModel) throws Exception {
		log.info("Getting RequestBody: " + userModel);
		Long registeredUser = userService.registerUser(userModel);
		return new ResponseEntity<Long>(registeredUser, HttpStatus.OK);
	}
	
	//Login
	@PostMapping("/login")
	public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {
		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							jwtRequest.getEmail(), jwtRequest.getPassword()));

		} 
		catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		log.info("Auth success");
		//If authentication successful, then get user details from userDetaislService
		final UserDetails userDetails = 
				appUserDetailsService.loadUserByUsername(jwtRequest.getEmail());
		if(Objects.isNull(userDetails)) {
			throw new UserNotFoundException();
		}
		//Generate token
		final String token = jwtUtility.generateToken(userDetails);
		return new JWTResponse(token);
	}
	
	
	//CUSTOMER
	
	//View Available RESTAURANTS
	@GetMapping("/customer/viewRestaurants")
	public ResponseEntity<List<Restaurant>> viewRestaurant(){
		List<Restaurant> restaurants = 
				userService.viewRestaurant();
		return new ResponseEntity<List<Restaurant>> (restaurants, HttpStatus.OK);
	}

	// Book table
	@PostMapping("/customer/bookTable")
	public ResponseEntity<Reservation> tableBooking(@RequestBody Reservation reservation) {
		Reservation reserved = 
				userService.tableBooking(reservation);
		return new ResponseEntity<Reservation>(reserved, HttpStatus.OK);
	}

	// Make payment
	@PostMapping("/customer/makePayment")
	public ResponseEntity<String> makePayment(@RequestBody MakePayment pay) throws Exception {
				userService.makePayment(pay);
		return new ResponseEntity<String>("Payment done successfully", HttpStatus.OK);
	}

	// Cancel booking
	@GetMapping("/customer/calcelBooking/{id}")
	public ResponseEntity<String> cancelBooking(@PathVariable long id) throws Exception{
				userService.cancelBooking(id);
		return new ResponseEntity<String>("Booking has been canceled", HttpStatus.OK);
	}
	
	// View Wallet balance
	@GetMapping("/customer/viewWalletBalance/{id}")
	public ResponseEntity<CustomerWalletResponse> viewWalletBalance(@PathVariable long id)throws Exception{
		CustomerWalletResponse response = 
				walletService.viewWalletBalance(id);
		return new ResponseEntity<CustomerWalletResponse>(response, HttpStatus.OK);
	}
	// Add Wallet balance
	@PostMapping("/customer/addMoneyToWallet")
	public ResponseEntity<String> addMoneyToWallet(@RequestBody AddMoneyToWallet aToWallet)throws Exception{
				walletService.addMoneyToWallet(aToWallet);
		return new ResponseEntity<String>("Money added to wallet successfully", HttpStatus.OK);
	}
	
	
	//	ADMIN
	
	//Add Restaurant
	@PostMapping("/admin/addRestaurant")
	public ResponseEntity<String> addRestaurant(@RequestBody Restaurant restaurant){
				userService.addRestaurant(restaurant);
		return new ResponseEntity<String>("Restaurant has been added", HttpStatus.OK);
	}
	
	//update Restaurant
	@PutMapping("/admin/updateRestaurant")
	public ResponseEntity<String> updateRestaurant(@RequestBody RestaurantModel restaurantModel) throws Exception{
				userService.updateRestaurant(restaurantModel);
		return new ResponseEntity<String>("Restaurant has been updated", HttpStatus.OK);
	}
	
	//delete Restaurant
	@DeleteMapping("/admin/deleteRestaurant/{id}")
	public ResponseEntity<String> deleteRestaurant(@PathVariable long id){
				userService.deleteRestaurant(id);
		return new ResponseEntity<String>("Restaurant has been deleted", HttpStatus.OK);
	}
	
	//approval request from manager (Service Provider)
	@GetMapping("/admin/approveManager/{email}")
	public ResponseEntity<String> approvalRequestFromManager(@PathVariable String email) throws Exception{
				userService.approvalRequestFromManager(email);
		return new ResponseEntity<String>("Request processed", HttpStatus.OK);
	}
	
	//Deactivate manager
	@GetMapping("/admin/deactivateManager/{email}")
	public ResponseEntity<String> deactivateManager(@PathVariable String email) throws Exception{
				userService.deactivateManager(email);
		return new ResponseEntity<String>("Request processed", HttpStatus.OK);
	}
	
	//MANAGER
	//Only activated manager can provide services and add/remove restaurant
//	@PostMapping("/manager/addTable")
//	public ResponseEntity<String> addTable(@RequestBody RestaurantTable table){
//		Long tableId = 	userService.addTable(table);
//		return new ResponseEntity<String>("Table added, Id: "+tableId, HttpStatus.OK);
//	}
	

}
