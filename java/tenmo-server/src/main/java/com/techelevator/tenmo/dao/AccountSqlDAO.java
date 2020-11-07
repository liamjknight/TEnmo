package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBalance(User user) {
		String sql = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, Math.toIntExact(user.getId()));
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
	public Account enactSuccessfulTransfer() {
		// TODO Auto-generated method stub
		return null;
	}

}
