package com.techelevator.tenmo.services;
import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfers;

public class AccountService {
	private final String BASE_SERVICE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	public AccountService(String baseUrl) {
		this.BASE_SERVICE_URL = baseUrl;
	}
	
	public BigDecimal getBalance(String authToken) {
		HttpEntity<?> entity = new HttpEntity<>(authHeaders(authToken));
		ResponseEntity<BigDecimal> response = restTemplate.exchange(
				BASE_SERVICE_URL + "balance/", HttpMethod.GET, entity, BigDecimal.class);
		return response.getBody();
		
	}
	
	public Transfers[] getAllTransfers(String authToken) {
		HttpEntity<?> entity = new HttpEntity<>(authHeaders(authToken));
		ResponseEntity<Transfers[]> response = restTemplate.exchange(BASE_SERVICE_URL + "transfers/",  HttpMethod.GET, entity, Transfers[].class);
		return response.getBody();
	}
	
	public Transfers getTransferId(String authToken, int id) {
		HttpEntity<?> entity = new HttpEntity<>(authHeaders(authToken));
		ResponseEntity<Transfers> response = restTemplate.exchange(BASE_SERVICE_URL + "transfers/" + id + "/",  HttpMethod.GET, entity, Transfers.class);
		return response.getBody();
	}
	
	private HttpHeaders authHeaders(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		return headers;
	}
	}