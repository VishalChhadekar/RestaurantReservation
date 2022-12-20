package com.phn.tech.RestaurantReservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phn.tech.RestaurantReservation.entity.Customer;
import com.phn.tech.RestaurantReservation.entity.Reservation;
import com.phn.tech.RestaurantReservation.entity.Restaurant;
import com.phn.tech.RestaurantReservation.model.AddMoneyToWallet;
import com.phn.tech.RestaurantReservation.model.CustomerWalletResponse;
import com.phn.tech.RestaurantReservation.model.MakePayment;
import com.phn.tech.RestaurantReservation.service.UserService;
import com.phn.tech.RestaurantReservation.service.WalletService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private WalletService walletService;


	// CUSTOMER REGISTRATION
	@PostMapping("/register")
	public ResponseEntity<Long> registerUser(@Valid @RequestBody Customer customer) {
		log.info("Getting RequestBody: " + customer);
		Long registeredCustomer = userService.registerCustomer(customer);
		return new ResponseEntity<Long>(registeredCustomer, HttpStatus.OK);
	}
	
	//View Available RESTAURANTID
	@GetMapping("/viewRestaurants")
	public ResponseEntity<List<Restaurant>> viewRestaurant(){
	List<Restaurant> restaurants = userService.viewRestaurant();
	return new ResponseEntity<List<Restaurant>> (restaurants, HttpStatus.OK);
	}

	// BOOK TABLE
	@PostMapping("/bookTable")
	public ResponseEntity<Reservation> tableBooking(@RequestBody Reservation reservation) {
		Reservation reserved = userService.tableBooking(reservation);
		return new ResponseEntity<Reservation>(reserved, HttpStatus.OK);
	}

	// Make payment
	@PostMapping("/makePayment")
	public ResponseEntity<String> makePayment(@RequestBody MakePayment pay) {
		userService.makePayment(pay);
		return new ResponseEntity<String>("Payment done successfully", HttpStatus.OK);
	}

	// Cancel booking
	@GetMapping("/calcelBooking/{id}")
	public ResponseEntity<String> cancelBooking(@PathVariable long id){
		userService.cancelBooking(id);
		return new ResponseEntity<String>("Booking has been canceled", HttpStatus.OK);
	}
	
	// View Wallet balance
	@GetMapping("/viewWalletBalance/{id}")
	public ResponseEntity<CustomerWalletResponse> viewWalletBalance(@PathVariable long id){
		CustomerWalletResponse response = walletService.viewWalletBalance(id);
		return new ResponseEntity<CustomerWalletResponse>(response, HttpStatus.OK);
	}
	// Add Wallet balance
	@PostMapping("/addMoneyToWallet")
	public ResponseEntity<String> addMoneyToWallet(@RequestBody AddMoneyToWallet aToWallet){
		walletService.addMoneyToWallet(aToWallet);
		return new ResponseEntity<String>("Money added to wallet successfully", HttpStatus.OK);
	}

}
