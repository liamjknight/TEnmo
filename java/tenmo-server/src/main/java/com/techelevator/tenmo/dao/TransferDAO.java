package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {
	
	List<Transfer> listTransfers(int id);
	List<Transfer> getTransferById(int userId, int transId);
	Transfer sendTransfer(int userId, TransferDTO transfer);
	List<Transfer> pendingTransfers(int id);
	User findUsernameById(int id);
	boolean approveRequest(int id);
	boolean denyRequest(int id);
	
	
}
