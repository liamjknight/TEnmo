package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {
	
	List<Transfer> listTransfers(int id);
	Transfer getTransferById(int userId, int transId);
	Transfer sendTransfer(Transfer transfer);
	Transfer requestTransfer(Transfer transfer);
	List<Transfer> pendingTransfers(int id);
	boolean approveRequest(int id, boolean accept);
	User findUsernameById(int id);
	
	
}
