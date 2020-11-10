package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {

	//validation annotations
	private int id;
	private int transferType;
	private int transferStatus;
	private User fromAccount;
	private User toAccount;
	private BigDecimal amountTransferred;
	
	public Transfer() { }

	public Transfer(int transferId, int transferType, int transferStatus, User fromAccount, User toAccount, BigDecimal amountTransferred) {
	      this.id = transferId;
	      this.transferType = transferType;
	      this.transferStatus = transferStatus;
	      this.fromAccount = fromAccount;
	      this.toAccount = toAccount;
	      this.amountTransferred = amountTransferred;
	   }
	
	
	@Override
	public String toString() { 		
		String transStatus = "";
		String transType = "";
		
		if (this.transferStatus==1) {transStatus = "Pending";} 
		else if (this.transferStatus==2) {transStatus = "Approved";} 
		else  if (this.transferStatus==3){transStatus = "Rejected";} 
		else {transStatus = "ERROR";}
		
		if (this.transferType==1) {transType = "Request";} 
		else if (this.transferType==2) {transType = "Send";}
		else {transStatus = "ERROR";}
		
		return "\n------------------------------" +
		"\n   Transaction Details" +
		"\n------------------------------" + 
        "\nTransaction ID: " + 
			id +
        "\nTransfer Type: " + 
			transType + 
		"\nTransfer Status: " + 
			transStatus + 
		"\nTRANSACTION: $" +
			amountTransferred +
		" from:" 
			+ fromAccount.getUsername().toUpperCase() +
				" to:"
					+ toAccount.getUsername().toUpperCase() +
						"\n";
	}
	
	public int getTransferId() {
		return id;
	}
	public void setTransferId(int transferId) {
		this.id = transferId;
	}
	public int getTransferType() {
		return transferType;
	}

	public void setTransferType(int transferType) {
		this.transferType = transferType;
	}

	public int getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(int transferStatus) {
		this.transferStatus = transferStatus;
	}

	public User getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(User fromAccount) {
		this.fromAccount = fromAccount;
	}

	public User getToAccount() {
		return toAccount;
	}

	public void setToAccount(User toAccount) {
		this.toAccount = toAccount;
	}

	public BigDecimal getAmountTransferred() {
		return amountTransferred;
	}
	public void setAmountTransferred(BigDecimal amountTransferred) {
		this.amountTransferred = amountTransferred;
	}
}
