package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {
	
	List<Transfer> listTransfers(User user);
	Transfer getTransferById(int id);
	Transfer sendTransfer(User user, Transfer transfer);
	Transfer requestTransfer(User user, Transfer transfer);
	List<Transfer> pendingTransfers(User user);
	//approval method?
	
}
