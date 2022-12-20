package com.phn.tech.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phn.tech.RestaurantReservation.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>{

}
