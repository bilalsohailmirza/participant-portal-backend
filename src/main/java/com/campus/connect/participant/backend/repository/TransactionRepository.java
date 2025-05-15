package com.campus.connect.participant.backend.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.campus.connect.participant.backend.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;


@Repository
public class TransactionRepository {

    @Autowired 
    JdbcTemplate jdbcTemplate;
    
    public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.fromString(rs.getString("id")));
        transaction.setSocietyId(UUID.fromString(rs.getString("society_id")));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setTransactionType(rs.getString("transaction_type"));
        transaction.setDescription(rs.getString("description"));
        transaction.setTitle(rs.getString("title"));
        transaction.setDate(rs.getDate("date").toLocalDate());
        return transaction;
    }
}


public void addTransaction(UUID id,UUID societyId, BigDecimal amount, String transactionType, String description, String title, LocalDate date) {
    String sql = "INSERT INTO \"transaction\" (id, society_id, amount, transaction_type, description, title, date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql,id, societyId, amount, transactionType, description, title, date);
}

// make a function to get all transactions by society id
public List<Transaction> getTransactionsBySocietyId(UUID societyId) {
    String sql = "SELECT * FROM \"transaction\" WHERE society_id = ?";
    return jdbcTemplate.query(sql, new TransactionRowMapper(), societyId);
}

// make a function to get all transactions by society id and transaction type
public List<Transaction> getTransactionsBySocietyIdAndType(UUID societyId, String transactionType) {
    String sql = "SELECT * FROM \"transaction\" WHERE society_id = ? AND transaction_type = ?";
    return jdbcTemplate.query(sql, new TransactionRowMapper(), societyId, transactionType);
}

// get total expense of a society
public int getTotalExpenseBySocietyId(UUID societyId) {
    String sql = "SELECT SUM(amount) FROM \"transaction\" WHERE society_id = ? AND transaction_type = 'expense'";
    Integer result = jdbcTemplate.queryForObject(sql, Integer.class, societyId);
    return result != null ? result : 0;
}

// get total income of a society
public int getTotalIncomeBySocietyId(UUID societyId) {
    String sql = "SELECT SUM(amount) FROM \"transaction\" WHERE society_id = ? AND transaction_type = 'income'";
    Integer result = jdbcTemplate.queryForObject(sql, Integer.class, societyId);
    return result != null ? result : 0;
}






}
