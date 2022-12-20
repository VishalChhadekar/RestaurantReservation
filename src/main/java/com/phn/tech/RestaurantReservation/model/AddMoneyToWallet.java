package com.phn.tech.RestaurantReservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyToWallet {
	private long custId;
	private double amount;

}
