package com.phn.tech.RestaurantReservation.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TableId")
	private long tableId;
	
	@Column(name = "TableNumber")
	private long tableNumber;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_Id", nullable=false)
	private Restaurant restaurant;
	
	@Column(name = "ISAVAILABLE")
	private boolean isAvailabe;
	

}
