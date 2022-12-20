package com.phn.tech.RestaurantReservation.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerWallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WALLETID")
	private long walletId;
	@Column(name = "CUSTID")
	private long custId;
	@Column(name = "AVAILABLEBALANCE")
	private double walletBalance;

}
