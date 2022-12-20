package com.phn.tech.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phn.tech.RestaurantReservation.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
