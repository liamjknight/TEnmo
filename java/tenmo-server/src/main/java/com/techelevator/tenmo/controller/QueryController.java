package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.AccountSqlDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.TransferSqlDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
//@PreAuthorize("isAuthenticated()")
@RequestMapping(path="/")
public class QueryController {
	
	private TransferDAO transferDAO;
	private AccountDAO accountDAO;
	private UserDAO userDAO;
	
	
	
	public QueryController(TransferSqlDAO transferSqlDAO, AccountSqlDAO accountSqlDAO, UserSqlDAO userSqlDAO) {
		this.transferDAO = transferSqlDAO;
		this.accountDAO = accountSqlDAO;
		this.userDAO = userSqlDAO;
	}
	
	@RequestMapping(path="balance/", method=RequestMethod.GET)//build this out
	public BigDecimal getBalance(@Valid @RequestParam int id) {
		return accountDAO.getBalance(0);
	}

	@RequestMapping(path="transfers/", method=RequestMethod.GET)
	public List<Transfer> listTransfers(@Valid @RequestParam int id){
		return transferDAO.listTransfers(id);
	}
	
	@RequestMapping(path="transfers/{id}", method=RequestMethod.GET)
	public Transfer getTransfer(@Valid @PathVariable int id) {
		return transferDAO.getTransferById(id);
	}
	
	@RequestMapping(path="transfers/", method=RequestMethod.POST)
	public Transfer sendTransfer(@Valid @RequestParam int id, @Valid @RequestBody Transfer transfer) {
		return transferDAO.sendTransfer(id, transfer);
	}
}
