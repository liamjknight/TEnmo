package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	
	List<Transfer> listAllTransfers();
	Transfer listTransferById(int id);
	Transfer sendTransfer();
	Transfer requestTransfer();
	List<Transfer> pendingTransfers();
	//approval method?
	
}
