package com.phn.tech.RestaurantReservation.service;

import com.phn.tech.RestaurantReservation.model.AddMoneyToWallet;
import com.phn.tech.RestaurantReservation.model.CustomerWalletResponse;

public interface WalletService {

	CustomerWalletResponse viewWalletBalance(long id) throws Exception;

	void addMoneyToWallet(AddMoneyToWallet aToWallet) throws Exception;
	
	void deductAmountFromWallet(double amount, long custId) throws Exception;

	void addMoneyToAdminWallet(double amount, long adminId) throws Exception;

	void addMoneyToManagersWallet(double amount, long managerId) throws Exception;

}
