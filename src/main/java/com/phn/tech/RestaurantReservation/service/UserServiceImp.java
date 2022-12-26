package com.phn.tech.RestaurantReservation.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.phn.tech.RestaurantReservation.entity.Reservation;
import com.phn.tech.RestaurantReservation.entity.Restaurant;
import com.phn.tech.RestaurantReservation.entity.RestaurantTable;
import com.phn.tech.RestaurantReservation.entity.Users;
import com.phn.tech.RestaurantReservation.exception.RestaurantNotFoundException;
import com.phn.tech.RestaurantReservation.exception.UserNotFoundException;
import com.phn.tech.RestaurantReservation.model.AddMoneyToWallet;

import com.phn.tech.RestaurantReservation.model.CustomerWalletResponse;
import com.phn.tech.RestaurantReservation.model.MakePayment;
import com.phn.tech.RestaurantReservation.model.RestaurantModel;
import com.phn.tech.RestaurantReservation.model.UserModel;
import com.phn.tech.RestaurantReservation.repository.ReservationRepository;
import com.phn.tech.RestaurantReservation.repository.RestaurantRepository;
//import com.phn.tech.RestaurantReservation.repository.RestaurantTableRepository;
import com.phn.tech.RestaurantReservation.repository.UsersRepository;

import jakarta.validation.Valid;

@Service
public class UserServiceImp implements UserService{
	

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
//	@Autowired
//	private RestaurantTableRepository restaurantTableRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private WalletService walletService;
	
	
	@Override
	public Long registerUser(@Valid UserModel userModel) throws Exception {
		Optional<Users> oUser = 
				usersRepository.findByEmail(userModel.getEmail());
		if(Objects.isNull(oUser)) {
			throw new UserNotFoundException();
		}
		if(oUser.isPresent()) {
			throw new RuntimeException("Username already exist!");
		}
		
		Users user = new Users();
		String encPass = passwordEncoder.encode(userModel.getPassword());
		userModel.setPassword(encPass);

		BeanUtils.copyProperties(userModel, user);
		usersRepository.save(user);
		return user.getUserId();
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
	public void makePayment(MakePayment pay) throws Exception {
		
		//Check: Whether customer have enough money in wallet to make payment or not.
		CustomerWalletResponse customerWalletResponse = 
				walletService.viewWalletBalance(pay.getCustId());
		
		if(customerWalletResponse.getWalletBalance()> pay.getAmount()) {
			Reservation reservation = reservationRepository.findByCustId(pay.getCustId());
			if(Objects.isNull(reservation)) {
				throw new RestaurantNotFoundException();
			}
			reservation.setBookingStatus("Booked");
			reservation.setPaymentStatus("Paid");
			reservation.setPaidAmount(pay.getAmount());
			reservationRepository.save(reservation);
			
		/*
		 Call walletService, and update the walletBalence
		 1. Customer: subtract paid amount from available balance.
		 2. Admin: add 10% of the amount to admin's wallet
		 3. Manager: add remaining amount to manager's wallet
			 
		*/
			walletService.deductAmountFromWallet(pay.getAmount(), pay.getCustId());
			walletService.addMoneyToAdminWallet((pay.getAmount()*1.1), pay.getAdminId());
			walletService.addMoneyToManagersWallet((pay.getAmount()*0.9), pay.getManagerId());
		}
		else {
			//sent message INSUFFICIENT WALLET BALANCE
		}
	}

	@Override
	public void cancelBooking(long id) throws Exception {
		Reservation reservation = 
				reservationRepository.findByCustId(id);

		if(reservation.getBookingStatus()=="Booked") {
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
	public void updateRestaurant(RestaurantModel restaurantModel) throws Exception {
		Restaurant restaurant = 
				restaurantRepository.findById(restaurantModel.getRestaurantId()).get();
		if(Objects.isNull(restaurant)) {
			throw new RestaurantNotFoundException();
		}
		
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
	public void approvalRequestFromManager(String email) throws Exception {
		Users user = 
				usersRepository.findByEmail(email).get();
		if(Objects.isNull(user)) {
			throw new UserNotFoundException();
		}
		user.setActive(true);
		usersRepository.save(user);
	}

	@Override
	public void deactivateManager(String email) throws Exception{
		Users user = 
				usersRepository.findByEmail(email).get();
		if(Objects.isNull(user)) {
			throw new UserNotFoundException();
		}
		user.setActive(false);
		usersRepository.save(user);
	}

//	@Override
//	public Long addTable(RestaurantTable table) {
//		RestaurantTable addedTable = restaurantTableRepository.save(table);
//		return addedTable.getTableId();
//	}
		
}
