package com.phn.tech.RestaurantReservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phn.tech.RestaurantReservation.entity.AdminWallet;
import com.phn.tech.RestaurantReservation.entity.CustomerWallet;
import com.phn.tech.RestaurantReservation.entity.ManagerWallet;
import com.phn.tech.RestaurantReservation.model.AddMoneyToWallet;
import com.phn.tech.RestaurantReservation.model.CustomerWalletResponse;
import com.phn.tech.RestaurantReservation.repository.AdminWalletRepository;
import com.phn.tech.RestaurantReservation.repository.CustomerWalletRepository;
import com.phn.tech.RestaurantReservation.repository.ManagerWalletRepository;

@Service
public class WalletServiceImp implements WalletService{
	
	@Autowired
	private CustomerWalletRepository customerWalletRepository;
	
	@Autowired
	private ManagerWalletRepository managerWalletRepository;
	
	@Autowired
	private AdminWalletRepository adminWalletRepository;

	@Override
	public CustomerWalletResponse viewWalletBalance(long id) {
		
		CustomerWallet customerWallet = customerWalletRepository.findByCustId(id);
		CustomerWalletResponse response = CustomerWalletResponse.builder()
				.CustId(id)
				.walletId(customerWallet.getWalletId())
				.walletBalance(customerWallet.getWalletBalance())
				.build();
		return response;
	}

	@Override
	public void addMoneyToWallet(AddMoneyToWallet aToWallet) {
		CustomerWallet customerWallet = 
				customerWalletRepository.findByCustId(aToWallet.getCustId());
		
		if(customerWallet==null) {
			CustomerWallet wallet = CustomerWallet.builder()
					.custId(aToWallet.getCustId())
					.walletBalance(aToWallet.getAmount())
					.build();
			customerWalletRepository.save(wallet);
		}
		
		customerWallet.setWalletBalance(aToWallet.getAmount());
		customerWalletRepository.save(customerWallet);
	}

	@Override
	public void deductAmountFromWallet(double amount, long custId) {
		CustomerWallet customerWallet = 
				customerWalletRepository.findByCustId(custId);
		
		customerWallet.setWalletBalance(customerWallet.getWalletBalance()-amount);
		customerWalletRepository.save(customerWallet);
		
	}

	@Override
	public void addMoneyToAdminWallet(double amount, long adminId) {
		AdminWallet adminWallet = 
				adminWalletRepository.findById(adminId).get();
		adminWallet.setWalletBalance(adminWallet.getWalletBalance()+amount);
		adminWalletRepository.save(adminWallet);
	}

	@Override
	public void addMoneyToManagersWallet(double amount, long managerId) {
		ManagerWallet managerWallet = 
				managerWalletRepository.findById(managerId).get();
		managerWallet.setWalletBalance(managerWallet.getWalletBalance()+amount);
		managerWalletRepository.save(managerWallet);
	}

}
