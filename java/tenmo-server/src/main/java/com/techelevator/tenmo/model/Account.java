package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
	
	private int accountId;
	private int userId;
	private BigDecimal balance;
	
	public Account() {}
	
	public Account(int accountId, int userId, BigDecimal balance) {
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
	}
	
	@Override
	public String toString() { 
		return 
		"\n------------------------------" +
		"\n   Account Details" +
		"\n------------------------------" + 
        "\nUser: " + userId +
        "\nAccount Number:" + accountId +
        "\nBalance: " + balance;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId=accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId=userId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal newBalanceShoes) {
		this.balance=newBalanceShoes;
	}
	
}
