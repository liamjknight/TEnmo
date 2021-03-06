package com.techelevator.tenmo.dao;
import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.SecureUserDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;


public interface AccountDAO {
	List<SecureUserDTO> listAllAccounts(int id);//probably not needed?
	BigDecimal getBalance(int id);
	boolean enactSuccessfulTransfer(TransferDTO transfer);//im... not sure this method should be here? hm..
	
}
