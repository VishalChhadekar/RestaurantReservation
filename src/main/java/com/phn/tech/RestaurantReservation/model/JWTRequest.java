package com.phn.tech.RestaurantReservation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTRequest {
	private String email;
	private String password;
	private String role;

}
