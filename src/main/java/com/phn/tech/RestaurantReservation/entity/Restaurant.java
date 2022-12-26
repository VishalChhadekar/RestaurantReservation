package com.phn.tech.RestaurantReservation.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESTAURANTID")
	private long restaurantId;
	
	@Column(name = "RESTAURANTNAME")
	private String restaurantName;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	List<RestaurantTable> tables = new ArrayList<>();

}
