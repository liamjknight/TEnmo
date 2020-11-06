package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;

public class Transfer {

	//validation annotations
	private int id;
	private int transferType;
	private int transferStatus;
	private Account fromAccount;
	private Account toAccount;
	private BigDecimal amountTransferred;
	
	public Transfer() { }

	public Transfer(int transferId, int transferType, int transferStatus, Account fromAccount, Account toAccount, BigDecimal amountTransferred) {
	      this.id = transferId;
	      this.transferType = transferType;
	      this.transferStatus = transferStatus;
	      this.fromAccount = fromAccount;
	      this.toAccount = toAccount;
	      this.amountTransferred = amountTransferred;
	   }
	
	
	@Override
	public String toString() { 
		//for this we need to to make a logical switch in case the transfer is being sent out or being received.
		return 
		"\n------------------------------" +
		"\n   Transaction Details" +
		"\n------------------------------" + 
        "\ntransaction ID: " + id +
        "\nTransfer Type: " + transferType +
        "\nTransfer Status: " + transferStatus +
        "\nSEND "+
        amountTransferred+
        " FROM: ~" + fromAccount +
        "~ TO: ~"+ toAccount+"~"+
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

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public BigDecimal getAmountTransfered() {
		return amountTransferred;
	}
	public void setAmountTransfered(BigDecimal amountTransferred) {
		this.amountTransferred = amountTransferred;
	}
}