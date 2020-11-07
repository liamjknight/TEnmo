package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

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
        SqlRowSet raw = jdbcTemplate.queryForRowSet("SELECT sum(balance) AS result FROM accounts WHERE user_id = ?;", user.getId());
        BigDecimal result = raw.getBigDecimal("result");
        return result;
	}

	@Override
	public Account enactSuccessfulTransfer() {
		// TODO Auto-generated method stub
		return null;
	}

}
