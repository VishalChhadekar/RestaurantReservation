package com.phn.tech.RestaurantReservation.service;

import com.phn.tech.RestaurantReservation.model.AddMoneyToWallet;
import com.phn.tech.RestaurantReservation.model.CustomerWalletResponse;

public interface WalletService {

	CustomerWalletResponse viewWalletBalance(long id);

	void addMoneyToWallet(AddMoneyToWallet aToWallet);
	
	void deductAmountFromWallet(double amount, long custId);

	void addMoneyToAdminWallet(double amount, long adminId);

	void addMoneyToManagersWallet(double amount, long managerId);

}
