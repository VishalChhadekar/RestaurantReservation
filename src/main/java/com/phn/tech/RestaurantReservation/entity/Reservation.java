package com.phn.tech.RestaurantReservation.entity;

import java.time.Instant;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESERVATIONID")
	private long ReservatinId;
	
	@Column(name = "CSUTOMERID")
	private long custId;
	
	@Column(name = "DATE_AND_TIME")
	private Instant ReservationDate;
	
	@Column(name = "TABLE_NUMBER")
	private long tableNumber;
	
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;
	
	@Column(name = "PAID_AMOUNT")
	private double paidAmount;
	
	@Column(name = "BOOKING_STATUS")
	private String bookingStatus;

}
