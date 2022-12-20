package com.phn.tech.RestaurantReservation.service;

import java.util.List;

import com.phn.tech.RestaurantReservation.entity.Customer;
import com.phn.tech.RestaurantReservation.entity.Manager;
import com.phn.tech.RestaurantReservation.entity.Reservation;
import com.phn.tech.RestaurantReservation.entity.Restaurant;
import com.phn.tech.RestaurantReservation.model.MakePayment;
import com.phn.tech.RestaurantReservation.model.RestaurantModel;

public interface UserService {

	Long registerCustomer(Customer customer);

	Reservation tableBooking(Reservation reservation);

	void makePayment(MakePayment pay);

	void cancelBooking(long id);

	List<Restaurant> viewRestaurant();

	void addRestaurant(Restaurant restaurant);

	void updateRestaurant(RestaurantModel restaurantModel);

	void deleteRestaurant(long id);

	Long registerManager(Manager manager);

	void approvalRequestFromManager(long id);

	void deactivateManager(long id);


}
