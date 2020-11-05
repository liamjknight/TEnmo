package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

	private int transferId;
	private int transferType;
	private int transferStatus;
	private int fromAccount;
	private int toAccount;
	private BigDecimal amountTransfered;
	
	@Override
	public String toString() { //for this we need to to make a logical switch in case the transfer is being sent out or being received.
		return "ID		From/To			Amount\n" +
			   "------------------------------\n" ;
	}
	
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
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

	public int getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(int fromAccount) {
		this.fromAccount = fromAccount;
	}

	public int getToAccount() {
		return toAccount;
	}

	public void setToAccount(int toAccount) {
		this.toAccount = toAccount;
	}

	public BigDecimal getAmountTransfered() {
		return amountTransfered;
	}
	public void setAmountTransfered(BigDecimal amountTransfered) {
		this.amountTransfered = amountTransfered;
	}
}
