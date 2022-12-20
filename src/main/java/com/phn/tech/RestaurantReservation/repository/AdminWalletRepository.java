package com.phn.tech.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phn.tech.RestaurantReservation.entity.AdminWallet;

@ResponseBody
public interface AdminWalletRepository extends JpaRepository<AdminWallet, Long> {

}
