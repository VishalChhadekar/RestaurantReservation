package com.phn.tech.RestaurantReservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ADMINID")
	private long adminId;
	
	@NotEmpty(message = "Name is required")
	@Column(name = "ADMIN_NAME")
	private String adminName;
	
	@NotEmpty(message = "Email is required")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email")
	@Column(name = "EMAIL")
	private String email; 
	
	@NotEmpty(message = "Password is required")
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ROLE")
	private String role = "admin";
}
