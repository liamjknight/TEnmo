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
	
	@Override
	public List<Transfer> getTransferById(int userId, int transId){
		List<Transfer> idTransfer = new ArrayList<Transfer>();
		String sql = "SELECT * FROM transfers " +
					 "WHERE transfer_id = ? AND (account_from = ? OR account_to = ?)";
		
		SqlRowSet raw = jdbcTemplate.queryForRowSet(sql, transId, userId, userId);
		while (raw.next()) {
			idTransfer.add(mapRowToTransfer(raw));
		}
		return idTransfer;
	}
	
	@Override
	public Transfer sendTransfer(int userId, TransferDTO transfer) {
		//if they are sending then 2, if its a request, 1
		int requestSend = (userId==transfer.getFromAccount()?2:1);
		String sqlForTransfer = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
					 			"VALUES(?, ?, ?, ?, ?);";
		Transfer result = new Transfer();
		// COMPARE (sender - transfer amount)>0 , i.e. sufficient funds		
		boolean sufficientFunds = accountDAO.getBalance(transfer.getFromAccount()).
				subtract(transfer.getAmountTransferred()).compareTo(new BigDecimal(0))>=0;
		//SUFFICIENT FUNDS + SENDING
		if(sufficientFunds&(requestSend==2)) {
			int raw = jdbcTemplate.update(sqlForTransfer, 
					requestSend, 2, transfer.getFromAccount(), 
					transfer.getToAccount(), transfer.getAmountTransferred());			
				if(raw==1) {
					String sqlToGetTransfer = "SELECT * FROM transfers WHERE transfer_id = (SELECT max(transfer_id) FROM transfers WHERE account_from = ?)";
					SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlToGetTransfer, transfer.getFromAccount());
				if (rowSet.next()) {
					result = mapRowToTransfer(rowSet);
					} else {System.out.print("ERROR");}
							boolean successfulTransfer = accountDAO.enactSuccessfulTransfer(transfer);
								if(successfulTransfer) 
									{return result;}
										else {return null;}
						} else {return null;}	}
		//REQUESTING PAYMENT
		else if (requestSend==1) {
			int raw = jdbcTemplate.update(sqlForTransfer, 
					requestSend, 1, transfer.getFromAccount(), 
					transfer.getToAccount(), transfer.getAmountTransferred());
				if(raw==1) {
					String sqlToGetTransfer = "SELECT * FROM transfers WHERE transfer_id = (SELECT max(transfer_id) FROM transfers WHERE account_from = ?)";
					SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlToGetTransfer, transfer.getFromAccount());
				if (rowSet.next()) {
					result = mapRowToTransfer(rowSet);
					} else {System.out.print("ERROR");}
				return result;
					} 	
		}
		return null;
	}
	@Override
	public boolean approveRequest(int id) {
		String sql = "UPDATE transfers SET transfer_status_id = 2 "+
				"WHERE transfer_id=? AND transfer_status_id = 1";
		int raw = jdbcTemplate.update(sql,id);
		if(raw==1) {return true;}
			else {return false;}
	}
	public boolean denyRequest(int id) {
		String sql = "UPDATE transfers SET transfer_status_id = 3 "+
				"WHERE transfer_id=? AND transfer_status_id = 1";
		int raw = jdbcTemplate.update(sql,id);
		if(raw==1) {return true;}
			else {return false;}
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
