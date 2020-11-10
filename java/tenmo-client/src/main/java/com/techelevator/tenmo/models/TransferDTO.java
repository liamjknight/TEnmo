package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class TransferDTO {
	private int fromAccount;
	private int toAccount;
	private BigDecimal amountTransferred;
	
	public TransferDTO() {}
	
	public void setSenderId(int id) {
		this.fromAccount = id;
	}
	public int getSenderId() {
		return this.fromAccount;
	}
	
	public void setReceiverId(int id) {
		this.toAccount = id;
	}
	public int getReceiverId() {
		return this.toAccount;
	}
	
	public void setSendAmount(BigDecimal sendAmount) {
		this.amountTransferred=sendAmount;
	}
	public BigDecimal getSendAmount() {
		return this.amountTransferred;
	}
}
