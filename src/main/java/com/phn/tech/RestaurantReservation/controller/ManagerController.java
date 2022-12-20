package com.phn.tech.RestaurantReservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.phn.tech.RestaurantReservation.entity.Manager;
import com.phn.tech.RestaurantReservation.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private UserService userService;
	
	/*
	 register
	 only active manager can provide service
	 manager can decline booking, if table is booked.
	 */
	
	// MANAGER REGISTRATION
	@PostMapping("/register")
	public ResponseEntity<Long> registerManager(@Valid @RequestBody Manager manager) {
		Long registeredManager = userService.registerManager(manager);
		return new ResponseEntity<Long>(registeredManager, HttpStatus.OK);
	}
	
	

}
