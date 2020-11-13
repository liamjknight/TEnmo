package com.techelevator.tenmo.services;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferDTO;
import com.techelevator.tenmo.models.User;

public class AccountService {
	private final String BASE_SERVICE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	public AccountService(String baseUrl) {
		this.BASE_SERVICE_URL = baseUrl;
	}
	
	public BigDecimal getBalance(AuthenticatedUser user) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<BigDecimal> response = restTemplate.exchange(BASE_SERVICE_URL + "balance/", HttpMethod.GET, entity, BigDecimal.class);
		return response.getBody();
	}
	public Transfer[] getAllTransfers(AuthenticatedUser user) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_SERVICE_URL + "transfers/",  HttpMethod.GET, entity, Transfer[].class);
		return response.getBody();
	}
	public Transfer[] getPendingRequests(AuthenticatedUser user) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_SERVICE_URL + "transfers/pending/",  HttpMethod.GET, entity, Transfer[].class);
		return response.getBody();
	}
	public Transfer[] getTransferId(AuthenticatedUser user, int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_SERVICE_URL + "transfers/" + id + "/",  HttpMethod.GET, entity, Transfer[].class);
		return response.getBody();
	}
	public Transfer[] enactTransfer(AuthenticatedUser user, int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		try {ResponseEntity<Transfer[]> transferReturn = restTemplate.exchange(BASE_SERVICE_URL + "transfers/"+id+"/approve/", HttpMethod.GET, entity, Transfer[].class);
			return transferReturn.getBody();} 
		catch (Exception e) {
			System.out.print("Insufficient funds. You have been fined $500.00.\n");
			return null;}
	}
	public Transfer[] denyTransfer(AuthenticatedUser user, int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		try {Transfer[] transferReturn = restTemplate.postForObject(BASE_SERVICE_URL + "transfers/"+id+"/deny/", entity, Transfer[].class);
			return transferReturn;} 
		catch (Exception e) {
			System.out.print("Unfortunately we sent your money to them anyway.\n");
			return null;}
		}
	public Transfer sendTransfer(AuthenticatedUser user,TransferDTO transfer) {
		Transfer transferReturn;
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
	    //headers.setContentType(MediaType.APPLICATION_JSON);
	    if (transfer == null) {System.out.print("ERROR");
	    						return null;}
		HttpEntity<TransferDTO> entity = new HttpEntity<>(transfer,headers);
		// IF (CURRENT USER IS SENDING MONEY)
		if (transfer.getFromAccount()==Math.toIntExact(user.getUser().getId())){
			try {transferReturn = restTemplate.postForObject(BASE_SERVICE_URL + "transfers/send/", entity, Transfer.class);
				return transferReturn;}
			catch (Exception e){System.out.print("ERROR");
								return null;}}
		else {try{transferReturn = restTemplate.postForObject(BASE_SERVICE_URL+"transfers/request/", entity, Transfer.class);
					return transferReturn;} 
			catch(Exception e) {System.out.print("ERROR");
							return null;}
						}
					}
	public User[] listAllUsers(AuthenticatedUser user){
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<User[]> response = restTemplate.exchange(BASE_SERVICE_URL + "users/", HttpMethod.GET, entity, User[].class);
		return response.getBody();
	}
	public Transfer makeNewTransferFromString(String csv, int transferType) {
		return new Transfer();
	}
}