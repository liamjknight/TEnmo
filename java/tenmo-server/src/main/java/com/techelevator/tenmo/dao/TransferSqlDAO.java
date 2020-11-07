package com.techelevator.tenmo.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@Service
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	@Override
	public List<Transfer> listTransfers(User user) {
		List<Transfer> results = new ArrayList<Transfer>();
		String sql = "SELECT * FROM transfers " + 
					 "WHERE account_from = ? OR account_to = ?;";
		
		SqlRowSet raw = jdbcTemplate.queryForRowSet(sql, user.getId(), user.getId());
		
		while(raw.next()) {
			results.add(mapToTransfer(raw));
		}
		
		return results;
	}
	
	@Override
	public Transfer getTransferById(int id) {
		Transfer result = new Transfer();
		String sql = "SELECT * FROM transfers " +
					 "WHERE transfer_id = ?;";
		
		SqlRowSet raw = jdbcTemplate.queryForRowSet(sql, id);
		
		result = mapToTransfer(raw);
		
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
	public Transfer mapToTransfer(SqlRowSet input) {
		Transfer result = new Transfer();
	
		result.setAmountTransfered(input.getBigDecimal("amount"));
		result.setFromAccount(input.getObject("account_from", Account.class));
		result.setToAccount(input.getObject("account_to", Account.class));
		result.setTransferId(input.getInt("transfer_id"));
		result.setTransferStatus(input.getInt("transfer_status"));
		result.setTransferType(input.getInt("transfer_type"));
		
		return result;
	}
}
