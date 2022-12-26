package com.phn.tech.RestaurantReservation.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.phn.tech.RestaurantReservation.entity.Reservation;
import com.phn.tech.RestaurantReservation.entity.Restaurant;
import com.phn.tech.RestaurantReservation.entity.RestaurantTable;
import com.phn.tech.RestaurantReservation.model.MakePayment;
import com.phn.tech.RestaurantReservation.model.RestaurantModel;
import com.phn.tech.RestaurantReservation.model.UserModel;

import jakarta.validation.Valid;

public interface UserService {

	Reservation tableBooking(Reservation reservation);

	void makePayment(MakePayment pay) throws Exception;

	void cancelBooking(long id) throws Exception;

	List<Restaurant> viewRestaurant();

	void addRestaurant(Restaurant restaurant);

	void updateRestaurant(RestaurantModel restaurantModel) throws Exception;

	void deleteRestaurant(long id);


	void approvalRequestFromManager(String email) throws Exception;

	void deactivateManager(String email) throws Exception;

	Long registerUser(@Valid UserModel userModel) throws Exception;

//	Long addTable(RestaurantTable table);

}
