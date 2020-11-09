package com.techelevator.tenmo.services;
import java.math.BigDecimal;

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
		System.out.print(user.getToken()+"\n\n\n\n");
		System.out.println(user.getUser().getId());
		headers.setBearerAuth(user.getToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		//String blah;
		//blah="{\"id\":"+user.getUser().getId()+"\n}";
		HttpEntity entity = new HttpEntity<>(headers);
		return restTemplate.exchange(BASE_SERVICE_URL + "balance/", HttpMethod.GET, entity, BigDecimal.class).getBody();
	}
	
	public Transfer[] getAllTransfers(String authToken) {
		HttpEntity<?> entity = new HttpEntity<>(authHeaders(authToken));
		ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_SERVICE_URL + "transfers/",  HttpMethod.GET, entity, Transfer[].class);
		return response.getBody();
	}
	
	public Transfer getTransferId(String authToken, int id) {
		HttpEntity<?> entity = new HttpEntity<>(authHeaders(authToken));
		ResponseEntity<Transfer> response = restTemplate.exchange(BASE_SERVICE_URL + "transfers/" + id + "/",  HttpMethod.GET, entity, Transfer.class);
		return response.getBody();
	}
	
	private HttpHeaders authHeaders(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		return headers;
		}
	}