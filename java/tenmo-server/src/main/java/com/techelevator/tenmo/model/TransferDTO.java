package com.techelevator.tenmo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferDTO {
	@NotNull
	Integer toAccount;
	@NotNull
	Integer fromAccount;
	@NotNull
	Integer transferType;
	@NotBlank
	@Positive
	BigDecimal amountTransferred;
	
	public Integer getToAccount() {
		return toAccount;
	}
	public void setToAccount(Integer toAccount) {
		this.toAccount = toAccount;
	}
	public Integer getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Integer fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Integer getTransferType() {
		return transferType;
	}
	public void setTransferType(Integer transferType) {
		this.transferType = transferType;
	}
	public BigDecimal getAmountTransferred() {
		return amountTransferred;
	}
	public void setAmountTransferred(BigDecimal amountTransferred) {
		this.amountTransferred = amountTransferred;
	}
}
