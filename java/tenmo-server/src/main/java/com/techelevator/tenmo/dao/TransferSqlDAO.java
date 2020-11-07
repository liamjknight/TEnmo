package com.techelevator.tenmo.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferNotFoundError;
import com.techelevator.tenmo.model.User;

@Service
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	@Override
	public List<Transfer> listTransfers(User user) {
		List<Transfer> userTransfers = new ArrayList<Transfer>();
		String sql = "SELECT * FROM transfers " + 
					 "WHERE account_from = ? OR account_to = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, Math.toIntExact(user.getId()), Math.toIntExact(user.getId()));
		while(result.next()) {
			userTransfers.add(mapRowToTransfer(result));
		}
		return userTransfers;
	}
	
	@Override
	public Transfer getTransferById(User user, int id){
		Transfer result = new Transfer();
		String sql = "SELECT * FROM transfers " +
					 "WHERE transfer_id = ? AND (account_from = ? OR account_to = ?)";
		
		SqlRowSet raw = jdbcTemplate.queryForRowSet(sql, id, Math.toIntExact(user.getId()), Math.toIntExact(user.getId()));
		if (raw.next()) {
			result = mapRowToTransfer(raw);
			
		} else {
			throw new TransferNotFoundError();
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
	public Transfer mapRowToTransfer(SqlRowSet input) {
		Transfer result = new Transfer();
		//result.setFromAccount(input.getObject("account_from", Account.class));
		//result.setToAccount(input.getObject("account_to", Account.class));
		result.setTransferId(input.getInt("transfer_id"));
		result.setTransferType(input.getInt("transfer_type_id"));
		result.setTransferStatus(input.getInt("transfer_status_id"));
		result.setFromAccount(input.getInt("account_from"));
		result.setToAccount(input.getInt("account_to"));
		result.setAmountTransfered(input.getBigDecimal("amount"));
		return result;
	}
}
