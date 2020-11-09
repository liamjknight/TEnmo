package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.UserNotActivatedException;

@Service
public class AccountSqlDAO implements AccountDAO {
	
    private JdbcTemplate jdbcTemplate;

    public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	public List<Account> listAllAccounts() {
		List<Account> accounts = new ArrayList<>();
		String sql = "SELECT user_id, username FROM users";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			Account account = mapRowToAccount(result);
			accounts.add(account);
		}
		return accounts;
	}

	@Override
	public BigDecimal getBalance(int id) {
		String sql = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
        //SqlRowSet result = jdbcTemplate.queryForRowSet(sql, Math.toIntExact(user.getId()));
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if (result.next()) {
        	BigDecimal balance = result.getBigDecimal(3);
        	return balance;
        } else {
        	throw new UserNotActivatedException("\nThere is an error with you account, or no account.\n");
        }
        //****There is no option to create a new account while already logged in.****
        //^^ if this changes, the sql must change to allow for more than one result
        //BigDecimal result = raw.getBigDecimal("result");
	}

	@Override
	public boolean enactSuccessfulTransfer(TransferDTO transfer) {
		String sqlToAdd_ToAccount = "UPDATE accounts SET balance = ? " + 
						  			"WHERE account_id = ?;";
		String sqlToSubtract_FromAccount = "UPDATE accounts SET balance = ? " + 
							   			   "WHERE account_id = ?;";
		BigDecimal fromAccountTotal = getBalance(transfer.getFromAccount());
		BigDecimal toAccountTotal = getBalance(transfer.getToAccount());
		BigDecimal transferAmount = transfer.getAmountTransferred();
		
		fromAccountTotal.subtract(transferAmount);
		toAccountTotal.add(transferAmount);
		
		int toWorked = jdbcTemplate.update(sqlToAdd_ToAccount, toAccountTotal, transfer.getToAccount());
		int fromWorked = jdbcTemplate.update(sqlToSubtract_FromAccount, fromAccountTotal, transfer.getFromAccount());
		
		boolean result = true;
		if(toWorked==0) {
			System.out.println("Could not update the To_Account.");
			result = false;
		}
		if(fromWorked==0) {
			System.out.println("Could not update From_Account.");
			result = false;
		}
		
		return result;
	}
	
	private Account mapRowToAccount(SqlRowSet input) {
		Account acc = new Account();
		acc.setAccountId(input.getInt("account_id"));
		acc.setUserId(input.getInt("user_id"));
		acc.setBalance(input.getBigDecimal("balance"));
		return acc;
	}

}
