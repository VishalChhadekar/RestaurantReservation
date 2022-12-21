package com.phn.tech.RestaurantReservation.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phn.tech.RestaurantReservation.entity.AdminWallet;
import com.phn.tech.RestaurantReservation.entity.CustomerWallet;
import com.phn.tech.RestaurantReservation.entity.ManagerWallet;
import com.phn.tech.RestaurantReservation.exception.WalletNotPresentException;
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
	public CustomerWalletResponse viewWalletBalance(long id) throws Exception {
		CustomerWallet customerWallet = customerWalletRepository.findByCustId(id);
		if(Objects.isNull(customerWallet)) {
			throw new WalletNotPresentException();
		}
		CustomerWalletResponse response = CustomerWalletResponse.builder()
				.CustId(id)
				.walletId(customerWallet.getWalletId())
				.walletBalance(customerWallet.getWalletBalance())
				.build();
		return response;
	}

	@Override
	public void addMoneyToWallet(AddMoneyToWallet aToWallet) throws Exception {
		CustomerWallet customerWallet = 
				customerWalletRepository.findByCustId(aToWallet.getCustId());
		if(Objects.isNull(customerWallet)) {
			throw new WalletNotPresentException();
		}
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
	public void deductAmountFromWallet(double amount, long custId)throws Exception {
		CustomerWallet customerWallet = 
				customerWalletRepository.findByCustId(custId);
		if(Objects.isNull(customerWallet)) {
			throw new WalletNotPresentException();
		}
		
		customerWallet.setWalletBalance(customerWallet.getWalletBalance()-amount);
		customerWalletRepository.save(customerWallet);
		
	}

	@Override
	public void addMoneyToAdminWallet(double amount, long adminId)throws Exception {
		AdminWallet adminWallet = 
				adminWalletRepository.findById(adminId).get();
		if(Objects.isNull(adminWallet)) {
			throw new WalletNotPresentException();
		}
		adminWallet.setWalletBalance(adminWallet.getWalletBalance()+amount);
		adminWalletRepository.save(adminWallet);
	}

	@Override
	public void addMoneyToManagersWallet(double amount, long managerId) throws Exception {
		ManagerWallet managerWallet = 
				managerWalletRepository.findById(managerId).get();
		if(Objects.isNull(managerWallet)) {
			throw new WalletNotPresentException();
		}
		managerWallet.setWalletBalance(managerWallet.getWalletBalance()+amount);
		managerWalletRepository.save(managerWallet);
	}

}
