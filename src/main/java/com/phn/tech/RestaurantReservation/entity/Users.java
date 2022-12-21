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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USERID")
	private Long userId;
	
	@NotEmpty(message = "Name is required")
	@Column(name = "USERNAME")
	private String name;
	
	@NotEmpty(message = "Email is required")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email")
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ACTIVE")
	private boolean active;
	
	@Column(name = "ROLE")
	private String role;
	
	@NotEmpty(message = "Password is required")
	@Column(name = "PASSWORD")
	private String password;

}
