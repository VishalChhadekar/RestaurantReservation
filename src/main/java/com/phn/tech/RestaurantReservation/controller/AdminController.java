package com.phn.tech.RestaurantReservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.phn.tech.RestaurantReservation.entity.Restaurant;
import com.phn.tech.RestaurantReservation.model.RestaurantModel;
import com.phn.tech.RestaurantReservation.service.UserService;



@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	//Add Restaurant
	@PostMapping("/addRestaurant")
	public ResponseEntity<String> addRestaurant(@RequestBody Restaurant restaurant){
		userService.addRestaurant(restaurant);
		return new ResponseEntity<String>("Restaurant has been added", HttpStatus.OK);
	}
	
	//update Restaurant
	@PutMapping("/updateRestaurant")
	public ResponseEntity<String> updateRestaurant(@RequestBody RestaurantModel restaurantModel){
		userService.updateRestaurant(restaurantModel);
		return new ResponseEntity<String>("Restaurant has been updated", HttpStatus.OK);
	}
	
	//delete Restaurant
	@DeleteMapping("/deleteRestaurant/{id}")
	public ResponseEntity<String> deleteRestaurant(@PathVariable long id){
		userService.deleteRestaurant(id);
		return new ResponseEntity<String>("Restaurant has been deleted", HttpStatus.OK);
	}
	
	//approval request from manager (Service Provider)
	@GetMapping("/approveManager/{id}")
	public ResponseEntity<String> approvalRequestFromManager(@PathVariable long id){
		userService.approvalRequestFromManager(id);
		return new ResponseEntity<String>("Request processed", HttpStatus.OK);
	}
	
	//Deactivate manager
	@GetMapping("/deactivateManager/{id}")
	public ResponseEntity<String> deactivateManager(@PathVariable long id){
		userService.deactivateManager(id);
		return new ResponseEntity<String>("Request processed", HttpStatus.OK);
	}
}
