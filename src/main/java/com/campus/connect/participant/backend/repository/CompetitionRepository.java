package com.campus.connect.participant.backend.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.campus.connect.participant.backend.model.Competition;

@Repository
public class CompetitionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map ResultSet to Competition object
    private final class CompetitionRowMapper implements RowMapper<Competition> {
        @Override
        public Competition mapRow(ResultSet rs, int rowNum) throws SQLException {
            Competition competition = new Competition();
            competition.setId(UUID.fromString(rs.getString("id")));
            competition.setName(rs.getString("name"));
            competition.setSocietyId(UUID.fromString(rs.getString("society_id")));
            competition.setDescription(rs.getString("description"));
            competition.setAbout(rs.getString("about"));
            competition.setLogo(rs.getString("logo"));
            competition.setCover(rs.getString("cover"));
            competition.setDate(rs.getDate("date").toLocalDate());
            competition.setTime(rs.getTime("time").toLocalTime());
            competition.setRuleBook(rs.getString("rule_book"));
            competition.setActivityId(UUID.fromString(rs.getString("activity_id")));
            return competition;
        }
    }

    // Fetch all competitions
    public List<Competition> findAll() {
        String sql = "SELECT * FROM competition";
        return jdbcTemplate.query(sql, new CompetitionRowMapper());
    }

    // Get competitions by society ID 
    public List<Map<String, Object>> findCompetitionsBySocietyId(UUID societyId) {
        String sql = "SELECT c.* FROM competition c where c.society_id = ?";
               
        return jdbcTemplate.queryForList(sql, societyId);
    }

    // Get details of a competition by ID    
    public Competition getDetails(UUID id) {
        String sql = "SELECT * FROM competition WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new CompetitionRowMapper(), id);
    }


}
