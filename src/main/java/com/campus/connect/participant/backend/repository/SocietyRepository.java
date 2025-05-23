package com.campus.connect.participant.backend.repository;

import com.campus.connect.participant.backend.model.Society;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class SocietyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Society> rowMapper = new RowMapper<Society>() {
        @Override
        public Society mapRow(ResultSet rs, int rowNum) throws SQLException {
            Society society = new Society();
            society.setId(UUID.fromString(rs.getString("id")));
            society.setName(rs.getString("name"));
            society.setDescription(rs.getString("description"));  // Updated
            society.setAbout(rs.getString("about"));              // Updated
            society.setLogo(rs.getString("logo"));                // Updated
            society.setCover(rs.getString("cover"));              // Updated
            return society;
        }
    };

    // Get all featured societies
    public List<Society> getAllFeaturedSocieties() {
        String sql = "SELECT * FROM \"society\"";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Get society by ID
    public Society getSocietyById(UUID id) {
        String sql = "SELECT * FROM \"society\" WHERE id = ?";
        List<Society> societies = jdbcTemplate.query(sql, rowMapper, id);
        return societies.isEmpty() ? null : societies.get(0);  // Safely return the first result, or null if not found
    }
    public Society getSocietyByName(String name)
    {
        String sql = "SELECT * FROM \"society\" WHERE name = ?";
        List<Society> societies = jdbcTemplate.query(sql, rowMapper, name);
        return societies.isEmpty() ? null : societies.get(0);  // Safely return the first result, or null if not found
    }

    // a function to add amount to society column total_budget
    public void addBudgetToSociety(UUID societyId, double amount) {
        String sql = "UPDATE \"society\" SET total_budget = total_budget + ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, societyId);
    }

    // a function to subtract amount from society column total_budget
    public void subtractBudgetFromSociety(UUID societyId, double amount) {
        String sql = "UPDATE \"society\" SET total_budget = total_budget - ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, societyId);
    }
    // a function to get the total budget of a society
    public double getTotalBudgetOfSociety(UUID societyId) {
        String sql = "SELECT total_budget FROM \"society\" WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, societyId);
    }

    
}
