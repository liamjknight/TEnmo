package com.techelevator.tenmo.dao;
import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import org.springframework.stereotype.Service;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	@Override
	public List<Transfer> listTransfers(User user) {
		List<Transfer> results = null;
		String sql = "SELECT * FROM transfers " + 
					 "WHERE account_from = ? OR account_to = ?;";
		
		try {
			results = jdbcTemplate.queryForList(sql, Transfer.class, user.getId(), user.getId());
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		return results;
	}
	
	@Override
	public Transfer getTransferById(int id) {
		Transfer result = null;
		String sql = "SELECT * FROM transfers " +
					 "WHERE transfer_id = ?;";
		SqlRowSet toParse = null;	
		
		try {
			toParse = jdbcTemplate.queryForRowSet(sql, Transfer.class, id);
			if(toParse!=null) {
				result.setAmountTransfered(toParse.getBigDecimal("amount"));//why will this be null?
				result.setFromAccount(toParse.getObject("account_from", Account.class));
				result.setToAccount(toParse.getObject("account_to", Account.class));
				result.setTransferId(toParse.getInt("transfer_id"));
				result.setTransferStatus(toParse.getInt("transfer_status"));
				result.setTransferType(toParse.getInt("transfer_type"));
			}
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		return result;
	}
	
	@Override
	public Transfer sendTransfer(User user, Transfer transfer) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Transfer requestTransfer(User user, Transfer transfer) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Transfer> pendingTransfers(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
