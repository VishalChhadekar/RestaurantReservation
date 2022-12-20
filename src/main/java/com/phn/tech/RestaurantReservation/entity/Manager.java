package com.phn.tech.RestaurantReservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MANAGERID")
	private long mgId;
	@Column(name = "MANAGER_NAME")
	
	@NotEmpty(message = "Name is required")
	private String managerName;
	
	@NotEmpty(message = "Email is required")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email")
	@Column(name = "EMAIL")
	private String email;
	
	@NotEmpty(message = "Password is required")
	@Column(name = "PASSWORD")
	private String password;
	
	@NotEmpty(message = "Phone number is required")
	@Pattern(regexp = "^[\\d]{10}$", message = "Phone number should be 10 digits")
	@Column(name = "MOBILE")
	private long mobile;
	
	@Column(name = "ACTIVE")
	private boolean active = false;
	
	@Column(name = "ROLE")
	private String role = "manager";

}
