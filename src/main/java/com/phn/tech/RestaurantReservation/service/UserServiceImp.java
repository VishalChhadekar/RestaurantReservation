package com.phn.tech.RestaurantReservation.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phn.tech.RestaurantReservation.entity.Admin;
import com.phn.tech.RestaurantReservation.entity.Customer;
import com.phn.tech.RestaurantReservation.entity.CustomerWallet;
import com.phn.tech.RestaurantReservation.entity.Manager;
import com.phn.tech.RestaurantReservation.entity.Reservation;
import com.phn.tech.RestaurantReservation.entity.Restaurant;
import com.phn.tech.RestaurantReservation.model.AddMoneyToWallet;
import com.phn.tech.RestaurantReservation.model.CustomerWalletResponse;
import com.phn.tech.RestaurantReservation.model.MakePayment;
import com.phn.tech.RestaurantReservation.model.RestaurantModel;
import com.phn.tech.RestaurantReservation.repository.AdminRepository;
import com.phn.tech.RestaurantReservation.repository.CustomerRepository;
import com.phn.tech.RestaurantReservation.repository.ManagerRepository;
import com.phn.tech.RestaurantReservation.repository.ReservationRepository;
import com.phn.tech.RestaurantReservation.repository.RestaurantRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImp implements UserService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private WalletService walletService;

	@Override
	public Long registerCustomer(Customer customer) {
		Customer cust = customerRepository.save(customer);
		return cust.getCustId();
	}
	
	@Override
	public List<Restaurant> viewRestaurant() {
		return restaurantRepository.findAll();
	}

	@Override
	public Reservation tableBooking(Reservation reservation) {
		Reservation reservation2 = Reservation.builder()
				.custId(reservation.getCustId())
				.ReservationDate(Instant.now())
				.tableNumber(reservation.getTableNumber())
				.bookingStatus("NA")
				.paymentStatus("NA")
				.paidAmount(0)
				.build();
		return reservationRepository.save(reservation2);
	}

	@Override
	public void makePayment(MakePayment pay) {
		
		//Check: Whether customer have enough money in wallet to make payment or not.
		
		CustomerWalletResponse customerWalletResponse = 
				walletService.viewWalletBalance(pay.getCustId());
		
		log.info("Wallet Balance: "+customerWalletResponse);
		
		if(customerWalletResponse.getWalletBalance()> pay.getAmount()) {
			Reservation reservation = reservationRepository.findByCustId(pay.getCustId());
			reservation.setBookingStatus("Booked");
			reservation.setPaymentStatus("Paid");
			reservation.setPaidAmount(pay.getAmount());
			reservationRepository.save(reservation);
			
		/*
		 Call walletService, and update the walletBalence
		 1. Customer: subtract paid amount from available balance.
		 2. Admin: add 10% of amount to admin's wallet
		 3. Manager: add remaining amount to manager's wallet
			 
		*/
			//Customer's Wallet;
			walletService.deductAmountFromWallet(pay.getAmount(), pay.getCustId());
			
			//Admin's Wallet: Assumption: Only one admin can login at a moment
			walletService.addMoneyToAdminWallet((pay.getAmount()*1.1), pay.getAdminId());
			
			//Manager's Wallet: 
			walletService.addMoneyToManagersWallet((pay.getAmount()*0.9), pay.getManagerId());
		}
		else {
			//sent message INSUFFICIENT WALLET BALANCE
		}
		
		
	}

	@Override
	public void cancelBooking(long id) {
		Reservation reservation = reservationRepository.findByCustId(id);

		if(reservation.getBookingStatus()!="Booked") {
			
			//Cancel booking     OR: We can Delete the row with CustId
			reservation.setBookingStatus("Canceled");
			reservation.setPaymentStatus("NA");
			reservation.setPaidAmount(0);
			reservationRepository.save(reservation);
			
			
			//refund: Note: Just for Demo purpose
			AddMoneyToWallet aToWallet = AddMoneyToWallet.builder()
					.custId(id)
					.amount(reservation.getPaidAmount())
					.build();
			walletService.addMoneyToWallet(aToWallet);
		}
		else {
			//sent email/display massage that: Booking has already been canceled.
		}
		
	}

	@Override
	public void addRestaurant(Restaurant restaurant) {
		restaurantRepository.save(restaurant);	
	}

	@Override
	public void updateRestaurant(RestaurantModel restaurantModel) {
		Restaurant restaurant = 
				restaurantRepository.findById(restaurantModel.getRestaurantId()).get();
		
		//update only if request body is not null
		if(restaurantModel.getRestaurantName()!=null) {
			restaurant.setRestaurantName(restaurantModel.getRestaurantName());
		}
		
		if(restaurantModel.getAddress()!=null) {
			restaurant.setAddress(restaurantModel.getAddress());
		}
		restaurantRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(long id) {
		restaurantRepository.deleteById(id);
	}

	@Override
	public Long registerManager(Manager manager) {
		Manager registeredManager = managerRepository.save(manager);
		return registeredManager.getMgId();
	}

	@Override
	public void approvalRequestFromManager(long id) {
		Manager manager = managerRepository.findById(id).get();
		manager.setActive(true);
		managerRepository.save(manager);
	}

	@Override
	public void deactivateManager(long id) {
		Manager manager = managerRepository.findById(id).get();
		manager.setActive(false);
		managerRepository.save(manager);
	}
	
}
