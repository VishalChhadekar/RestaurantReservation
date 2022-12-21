package com.phn.tech.RestaurantReservation.service;

import java.util.List;

import com.phn.tech.RestaurantReservation.entity.Reservation;
import com.phn.tech.RestaurantReservation.entity.Restaurant;

import com.phn.tech.RestaurantReservation.model.MakePayment;
import com.phn.tech.RestaurantReservation.model.RestaurantModel;
import com.phn.tech.RestaurantReservation.model.UserModel;

import jakarta.validation.Valid;

public interface UserService {

	Reservation tableBooking(Reservation reservation);

	void makePayment(MakePayment pay);

	void cancelBooking(long id);

	List<Restaurant> viewRestaurant();

	void addRestaurant(Restaurant restaurant);

	void updateRestaurant(RestaurantModel restaurantModel);

	void deleteRestaurant(long id);


	void approvalRequestFromManager(String email);

	void deactivateManager(String email);

	Long registerUser(@Valid UserModel userModel);


}
