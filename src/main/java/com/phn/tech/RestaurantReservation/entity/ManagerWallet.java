package com.phn.tech.RestaurantReservation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerWallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long walletId;
	private long managerId;
	private double walletBalance;

}
