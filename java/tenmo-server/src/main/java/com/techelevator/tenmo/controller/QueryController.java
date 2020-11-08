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
	
	@RequestMapping(path="balance/", method=RequestMethod.GET)
	public BigDecimal getBalance(@RequestBody User user) {
		return accountDAO.getBalance(user);
	}

	@RequestMapping(path="transfers/", method=RequestMethod.GET)
	public List<Transfer> listUserTransfers(@RequestBody User user){
		return transferDAO.listTransfers(user);
	}
	
	@RequestMapping(path="transfers/pending/", method=RequestMethod.GET)
	public List<Transfer> listPendingTransfers(@RequestBody User user){
		return transferDAO.pendingTransfers(user);
	}
	
	@RequestMapping(path="transfers/{id}/", method=RequestMethod.GET)
	public Transfer getTransferById(@RequestBody User user, @Valid @PathVariable int id) {
		return transferDAO.getTransferById(user, id);
	}
	
	@RequestMapping(path="transfers/request/", method=RequestMethod.POST)//I need to build out SQL
	public Transfer requestTransfer(@Valid @RequestBody Transfer transfer) {
		return transferDAO.requestTransfer(transfer);
	}
	
	@RequestMapping(path="transfers/send/", method=RequestMethod.POST)
	public Transfer sendTransfer(@Valid @RequestBody Transfer transfer) {
		return transferDAO.sendTransfer(transfer);
	}
	
	@RequestMapping(path="transfers/pending/", method=RequestMethod.PUT)
	public boolean approveTransfer(@RequestBody int id, @RequestBody boolean accept) {
		return transferDAO.approveRequest(id, accept);
	}
	
}
