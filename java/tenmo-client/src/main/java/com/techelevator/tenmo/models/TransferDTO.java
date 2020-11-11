package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class TransferDTO {
	private int fromAccount;
	private int toAccount;
	private BigDecimal amountTransferred;
	
	public TransferDTO() {}
	
	public void setFromAccount(int id) {
		this.fromAccount = id;
	}
	public int getFromAccount() {
		return this.fromAccount;
	}
	
	public void setToAccount(int id) {
		this.toAccount = id;
	}
	public int getToAccount() {
		return this.toAccount;
	}
	
	public void setAmountTransferred(BigDecimal sendAmount) {
		this.amountTransferred=sendAmount;
	}
	public BigDecimal getAmountTransferred() {
		return this.amountTransferred;
	}
}
