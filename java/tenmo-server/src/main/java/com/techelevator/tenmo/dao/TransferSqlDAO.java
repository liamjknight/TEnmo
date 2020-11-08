package com.techelevator.tenmo.dao;
import java.math.BigDecimal;
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
		Transfer result = transfer;
		String sqlForTransfer = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
					 			"VALUES(2, ?, ?, ?, ?);";
		String sqlToCheckAccountBalance = "SELECT balance FROM accounts WHERE user_id = ?;";
		
		SqlRowSet accountBalanceRaw = jdbcTemplate.queryForRowSet(sqlToCheckAccountBalance, user.getId());
		
		if(accountBalanceRaw.next()) {
			BigDecimal accountBalance = accountBalanceRaw.getBigDecimal("balance");
			
			if(accountBalance.compareTo(transfer.getAmountTransfered())>=0) {
				jdbcTemplate.update(sqlForTransfer, 2, user.getId(), transfer.getToAccount(), transfer.getAmountTransfered());
				return result;
			}else {
				System.out.println("You do not have the required funds to make this transfer.");
				return new Transfer();
			}
		}else {
			System.out.println("No accounts found for this user.");
			return new Transfer();
		}
	}
	@Override
	public Transfer requestTransfer(User user, Transfer transfer) {
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
	public List<Transfer> pendingTransfers(User user) {
		List<Transfer> userTransfers = new ArrayList<Transfer>();
		String sql = "SELECT * FROM transfers " + 
				 "WHERE (account_from = ? OR account_to = ?) AND transfer_status_id = 1";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, Math.toIntExact(user.getId()), Math.toIntExact(user.getId()));
		while(result.next()) {
			userTransfers.add(mapRowToTransfer(result));
		}
		return userTransfers;
	}
	public Transfer mapRowToTransfer(SqlRowSet input) {
		Transfer result = new Transfer();
		//having trouble with extracting account_from and account_to fields as custom Account object
		//so for now, we can treat account number as an int, and retrieve account info manually when needed?
		//which might get annoying once we are adding transfers and determining whether they succeed or fail.
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
