package com.techelevator.tenmo.dao;
import java.util.List;
import com.techelevator.tenmo.model.Transfer;
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
	public List<Transfer> listAllTransfers() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Transfer listTransferById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Transfer> listTransferByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Transfer sendTransfer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Transfer requestTransfer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Transfer> pendingTransfers() {
		// TODO Auto-generated method stub
		return null;
	}
}
