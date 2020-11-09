package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {
	
	List<Transfer> listTransfers(int id);
	Transfer getTransferById(User user, int id);
	Transfer sendTransfer(Transfer transfer);
	Transfer requestTransfer(Transfer transfer);
	List<Transfer> pendingTransfers(User user);
	boolean approveRequest(int id, boolean accept);
	User findUsernameById(int id);
	
	
}
