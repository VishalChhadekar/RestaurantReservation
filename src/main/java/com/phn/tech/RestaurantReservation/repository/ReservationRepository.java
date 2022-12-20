package com.phn.tech.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phn.tech.RestaurantReservation.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Reservation findByCustId(long custId);

}
