package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.SecureUserDTO;
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
	public List<SecureUserDTO> listAllAccounts(int id) {
		List<SecureUserDTO> users = new ArrayList<>();
		String sql = "SELECT user_id, username FROM users WHERE user_id != ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql,id);
		while (result.next()) {
			SecureUserDTO user = mapRowToSecureUser(result);
			users.add(user);
		}
		return users;
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
		boolean result = false;
		String sql = "UPDATE accounts SET balance = ? " + 
						  			"WHERE account_id = ?;";
		
		BigDecimal fromAccountTotal = getBalance(transfer.getFromAccount());
		BigDecimal toAccountTotal = getBalance(transfer.getToAccount());
		BigDecimal transferAmount = transfer.getAmountTransferred();
		
		fromAccountTotal = fromAccountTotal.subtract(transferAmount);
		toAccountTotal = toAccountTotal.add(transferAmount);
		
		try {
			int fromWorked = jdbcTemplate.update(sql, fromAccountTotal, transfer.getFromAccount());
			int toWorked = jdbcTemplate.update(sql, toAccountTotal, transfer.getToAccount());
			
			if (fromWorked==1&toWorked==1) {
				result = true;
			}
		} catch (Exception e) {
			System.out.print("ERROR");
			jdbcTemplate.update("ROLLBACK");
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
	private SecureUserDTO mapRowToSecureUser(SqlRowSet input) {
		SecureUserDTO user = new SecureUserDTO();
		user.setId(input.getLong("user_id"));
		user.setUsername(input.getString("username"));
		return user;
	}

}
