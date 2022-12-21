package com.phn.tech.RestaurantReservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESTAURANTID")
	private long restaurantId;
	
	@Column(name = "RESTAURANTNAME")
	private String restaurantName;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "TABLECOUNT")
	private long tableCount;

}
