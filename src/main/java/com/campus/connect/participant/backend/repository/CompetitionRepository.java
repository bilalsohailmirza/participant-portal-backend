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
import com.campus.connect.participant.backend.model.Event;

@Repository
public class CompetitionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final class CompetitionRowMapper implements RowMapper<Competition> {
    @Override
    public Competition mapRow(ResultSet rs, int rowNum) throws SQLException {
        Competition competition = new Competition();
        competition.setId(UUID.fromString(rs.getString("id")));
        competition.setName(rs.getString("name"));
        competition.setImage(rs.getString("image"));
        competition.setFee(rs.getInt("fee"));
        competition.setActivityId(UUID.fromString(rs.getString("activity_id")));
        return competition;
    }
}

    public List<Map<String, Object>> findCompetitionsBySocietyId(UUID societyId) {
         
        String sql = "SELECT c.*,a.start_date,a.end_date FROM competition c join \"Activities\" a on c.activity_id = a.id where a.society_id = ? "; // Using '?' to safely bind the societyId parameter
        
        // Execute the query with the societyId parameter and return the list of events
        return jdbcTemplate.queryForList(sql, societyId);
    }

    public Competition getDetails(UUID id)
    {
        String sql = "Select * FROM competition where id = ?";
        return jdbcTemplate.queryForObject(sql, new CompetitionRowMapper(), id);
    }
}
