package com.phn.tech.RestaurantReservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

	private Long id;
	private String name;
	private String email;
	private boolean disabled = false;
	private String role;
	private String password;

}
