package com.phn.tech.RestaurantReservation.model;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerWalletResponse {
	private long walletId;
	private long CustId;
	private double walletBalance;
}
