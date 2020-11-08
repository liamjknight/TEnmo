package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfers {
	private int id;
	private int transferType;
	private int transferStatus;
	private int fromAccount;
	private int toAccount;
	private BigDecimal amount;
	
	

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
		return amount;
	}
	public void setAmountTransfered(BigDecimal amountTransferred) {
		this.amount = amountTransferred;
	}
}
