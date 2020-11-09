package com.techelevator.tenmo.dao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.TransferNotFoundError;
import com.techelevator.tenmo.model.User;

@Service
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;
	private AccountDAO accountDAO;
	

    public TransferSqlDAO(JdbcTemplate jdbcTemplate, AccountDAO accountDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDAO = accountDAO;
    }
	@Override
	public List<Transfer> listTransfers(int id) {
		List<Transfer> userTransfers = new ArrayList<Transfer>();
		String sql = "SELECT * FROM transfers " + 
					 "WHERE account_from = ? OR account_to = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id, id);
		while(result.next()) {
			userTransfers.add(mapRowToTransfer(result));
		}
		return userTransfers;
	}
	
	@Override
	public Transfer getTransferById(int userId, int id){
		Transfer result = new Transfer();
		String sql = "SELECT * FROM transfers " +
					 "WHERE transfer_id = ? AND (account_from = ? OR account_to = ?)";
		
		SqlRowSet raw = jdbcTemplate.queryForRowSet(sql, id, userId, userId);
		if (raw.next()) {
			result = mapRowToTransfer(raw);
			
		} else {
			throw new TransferNotFoundError();
		}
		return result;
	}
	
	@Override
	public Transfer sendTransfer(TransferDTO transfer) {
		String sqlForTransfer = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
					 			"VALUES(?, ?, ?, ?, ?);";
		
		System.out.println(accountDAO.getBalance(1));
		
		
		if(accountDAO.getBalance(transfer.getFromAccount()).subtract(transfer.getAmountTransferred()).compareTo(new BigDecimal(0))>=0) {
			int raw = jdbcTemplate.update(sqlForTransfer, 2, 2, transfer.getFromAccount(), transfer.getToAccount(), transfer.getAmountTransferred());
			
			if(raw!=0) {
				String sqlToGetTransfer = "SELECT * FROM transfers WHERE transfer_id = (SELECT max(transfer_id) FROM transfers WHERE account_from = ?)";
				SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlToGetTransfer, transfer.getFromAccount());
				Transfer result = new Transfer();
				
				while(rowSet.next()) {
					result = mapRowToTransfer(rowSet);
				}
				
				boolean successfulTransfer = accountDAO.enactSuccessfulTransfer(transfer);
				
				if(successfulTransfer) {
					return result;
				}else {
					return null;
				}
			}else {
				System.out.println("Something broke in your transfer!");
				
				return null;
			}
		}else {
			System.out.println("You do not have the funds to make this transfer.");
			
			return null;
		}
		
		
		
	}
	@Override
	public Transfer requestTransfer(TransferDTO transfer) {
		return null;
	}
	@Override
	public boolean approveRequest(int id, boolean accept) {
		String sql = "";//this needs to be a update sql statement with a ? for the transfer id and transfer status.
		
		if(accept) {
			//set transfer to accepted
			return true;
		}else {
			//set transfer to rejected
			return false;
		}
	}
	@Override
	public List<Transfer> pendingTransfers(int id) {
		List<Transfer> userTransfers = new ArrayList<Transfer>();
		String sql = "SELECT * FROM transfers " + 
				 "WHERE (account_from = ? OR account_to = ?) AND transfer_status_id = 1";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id, id);
		while(result.next()) {
			userTransfers.add(mapRowToTransfer(result));
		}
		return userTransfers;
	}
	public Transfer mapRowToTransfer(SqlRowSet input) {		
		Transfer result = new Transfer();
		result.setTransferId(input.getInt("transfer_id"));
		result.setTransferType(input.getInt("transfer_type_id"));
		result.setTransferStatus(input.getInt("transfer_status_id"));
		User from = findUsernameById(input.getInt("account_from"));
			result.setFromAccount(from);
		User to = findUsernameById(input.getInt("account_to"));
			result.setToAccount(to);
		result.setAmountTransferred(input.getBigDecimal("amount"));
		return result;
	}
	@Override
	public User findUsernameById(int id) {
		User user = new User();
		String sql = "SELECT username FROM users WHERE user_id=?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql,id);
		while(result.next()) {
			user.setUsername(result.getString(1));
			user.setId((long)id);
		}
		return user;
	}
	
}
