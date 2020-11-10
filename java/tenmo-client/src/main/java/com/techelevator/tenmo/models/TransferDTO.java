package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class TransferDTO {
	private int senderId;
	private int receiverId;
	private BigDecimal sendAmount;
	
	public TransferDTO() {}
	
	public void setSenderId(int id) {
		this.senderId = id;
	}
	public int getSenderId() {
		return this.senderId;
	}
	
	public void setReceiverId(int id) {
		this.receiverId = id;
	}
	public int getReceiverId() {
		return this.receiverId;
	}
	
	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount=sendAmount;
	}
	public BigDecimal getSendAmount() {
		return this.sendAmount;
	}
}
