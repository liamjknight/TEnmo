package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;

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
	public BigDecimal getBalance(int id) {
        return jdbcTemplate.queryForObject("select balance from accounts where user_id = ?", BigDecimal.class, id);
	}

	@Override
	public Account enactSuccessfulTransfer() {
		// TODO Auto-generated method stub
		return null;
	}

}
