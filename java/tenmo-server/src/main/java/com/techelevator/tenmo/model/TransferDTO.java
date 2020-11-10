package com.techelevator.tenmo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferDTO {
	@NotNull
	int toAccount;
	@NotNull
	int fromAccount;
	//@NotNull
	//int transferType;
	@NotBlank
	@Positive
	BigDecimal amountTransferred;
	
	public int getToAccount() {
		return toAccount;
	}
	public void setToAccount(int toAccount) {
		this.toAccount = toAccount;
	}
	public int getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(int fromAccount) {
		this.fromAccount = fromAccount;
	}
	//public int getTransferType() {
	//	return transferType;
	//}
	//public void setTransferType(int transferType) {
	//	this.transferType = transferType;
	//}
	public BigDecimal getAmountTransferred() {
		return amountTransferred;
	}
	public void setAmountTransferred(BigDecimal amountTransferred) {
		this.amountTransferred = amountTransferred;
	}
}
