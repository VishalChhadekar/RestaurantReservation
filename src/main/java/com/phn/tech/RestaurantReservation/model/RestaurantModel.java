package com.phn.tech.RestaurantReservation.model;

import com.phn.tech.RestaurantReservation.entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RestaurantModel {

	private long restaurantId;
	private String restaurantName;
	private String address;
}
