package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@RestController
@PreAuthorize("isAuthorized()")
@RequestMapping(path="/")
public class QueryController {
	
	private TransferDAO transferDAO;
	private AccountDAO accountDAO;
	
	@RequestMapping(path="balance/", method=RequestMethod.GET)//build this out
	public BigDecimal getBalance() {
		return accountDAO.getBalance();
	}

	@RequestMapping(path="transfers/", method=RequestMethod.GET)
	public List<Transfer> listTransfers(){
		return transferDAO.listAllTransfers();
	}
	
	@RequestMapping(path="transfer/{id}", method=RequestMethod.GET)
	public Transfer getTransfer(@PathVariable int id) {
		return null;
	}
}
