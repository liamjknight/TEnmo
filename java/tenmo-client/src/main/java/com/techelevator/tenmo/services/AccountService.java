package com.techelevator.tenmo.services;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
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
	
	public Transfer getTransferId(AuthenticatedUser user, int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(user.getToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Transfer> response = restTemplate.exchange(BASE_SERVICE_URL + "transfers/{" + id + "}/",  HttpMethod.GET, entity, Transfer.class);
		return response.getBody();
	}
}