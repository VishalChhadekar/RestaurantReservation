package com.phn.tech.RestaurantReservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MakePayment {
	private long custId;
	private long adminId;
	private long managerId;
	private double amount;

}
