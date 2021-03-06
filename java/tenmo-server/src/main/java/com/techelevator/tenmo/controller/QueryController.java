package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.techelevator.tenmo.model.SecureUserDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;

import io.jsonwebtoken.Jwt;

@RestController
@PreAuthorize("isAuthenticated()")
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
	public BigDecimal getBalance(HttpServletRequest request) {
		Principal token = request.getUserPrincipal();
		//System.out.print(token.hashCode());
		//System.out.print(token.getName());
		int id = userDAO.findIdByUsername(token.getName());
		return accountDAO.getBalance(id);
	}
	@RequestMapping(path="transfers/", method=RequestMethod.GET)
	public List<Transfer> listUserTransfers(HttpServletRequest request){
		Principal token = request.getUserPrincipal();
		int id = userDAO.findIdByUsername(token.getName());
		return transferDAO.listTransfers(id);
	}
	@RequestMapping(path="transfers/pending/", method=RequestMethod.GET)
	public List<Transfer> listPendingTransfers(HttpServletRequest request){
		Principal token = request.getUserPrincipal();
		int id = userDAO.findIdByUsername(token.getName());
		return transferDAO.pendingTransfers(id);
	}
	@RequestMapping(path="transfers/{id}/", method=RequestMethod.GET)
	public List<Transfer> getTransferById(HttpServletRequest request, @Valid @PathVariable int id) {
		Principal token = request.getUserPrincipal();
		int userId = userDAO.findIdByUsername(token.getName());
		return transferDAO.getTransferById(userId, id);
	}
	@RequestMapping(path="users/",method=RequestMethod.GET)
	public List<SecureUserDTO> listUsers(HttpServletRequest request){
		Principal token = request.getUserPrincipal();
		int id = userDAO.findIdByUsername(token.getName());
		return accountDAO.listAllAccounts(id);
	}
	@RequestMapping(path="transfers/request/", method=RequestMethod.POST)//I need to build out SQL
	public Transfer requestTransfer(@RequestBody TransferDTO transfer, HttpServletRequest request) {
		Principal token = request.getUserPrincipal();
		int userId = userDAO.findIdByUsername(token.getName());
		return transferDAO.sendTransfer(userId, transfer);
	}
	@RequestMapping(path="transfers/send/", method=RequestMethod.POST)
	public Transfer sendTransfer(@RequestBody TransferDTO transfer, HttpServletRequest request) {
		Principal token = request.getUserPrincipal();
		int userId = userDAO.findIdByUsername(token.getName());
		return transferDAO.sendTransfer(userId, transfer);
	}
	@RequestMapping(path="transfers/{id}/approve/", method=RequestMethod.GET)
	public List<Transfer> approveTransfer(HttpServletRequest request, @PathVariable int id) {
		Principal token = request.getUserPrincipal();
		int userId = userDAO.findIdByUsername(token.getName());
		List<Transfer> transfer = transferDAO.getTransferById(userId, id);
		TransferDTO dto = new TransferDTO();
			dto.setFromAccount(Math.toIntExact(transfer.get(0).getFromAccount().getId()));
			dto.setToAccount(Math.toIntExact(transfer.get(0).getToAccount().getId()));
			dto.setAmountTransferred(transfer.get(0).getAmountTransferred());
		accountDAO.enactSuccessfulTransfer(dto);
		transferDAO.approveRequest(id);
		return transferDAO.getTransferById(userId, id);
	}
	
	@RequestMapping(path="transfers/{id}/deny/", method=RequestMethod.POST)
	public List<Transfer> denyTransfer(HttpServletRequest request, @PathVariable int id) {
		Principal token = request.getUserPrincipal();
		int userId = userDAO.findIdByUsername(token.getName());
		transferDAO.denyRequest(id);
		return transferDAO.getTransferById(userId, id);
	}
	
}
