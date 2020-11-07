package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {
	
	List<Transfer> listTransfers(int id);
	Transfer getTransferById(int id);
	Transfer sendTransfer(int id, Transfer transfer);
	Transfer requestTransfer(int id, Transfer transfer);
	List<Transfer> pendingTransfers(int id);
	//approval method?
	
}
