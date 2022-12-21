package com.phn.tech.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phn.tech.RestaurantReservation.entity.CustomerWallet;

@Repository
public interface CustomerWalletRepository extends JpaRepository<CustomerWallet, Long>{

	CustomerWallet findByCustId(long id);

}
