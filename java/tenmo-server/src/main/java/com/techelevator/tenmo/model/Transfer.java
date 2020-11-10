package com.techelevator.tenmo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Transfer {

	//validation annotations
	private int id;
	private int transferType;
	private int transferStatus;
	private User fromAccount;
	private User toAccount;
	private BigDecimal amountTransfered;
	
	public Transfer() { }

	public Transfer(int transferId, int transferType, int transferStatus, User fromAccount, User toAccount, BigDecimal amountTransfered) {
	      this.id = transferId;
	      this.transferType = transferType;
	      this.transferStatus = transferStatus;
	      this.fromAccount = fromAccount;
	      this.toAccount = toAccount;
	      this.amountTransfered = amountTransfered;
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
		return amountTransfered;
	}
	public void setAmountTransferred(BigDecimal amountTransfered) {
		this.amountTransfered = amountTransfered;
	}
}
