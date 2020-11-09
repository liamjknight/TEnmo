package com.techelevator.tenmo.dao;
import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;


public interface AccountDAO {
	List<Account> listAllAccounts();//probably not needed?
	BigDecimal getBalance(int id);
	Account enactSuccessfulTransfer();//im... not sure this method should be here? hm..
	
}
