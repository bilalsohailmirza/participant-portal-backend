package com.campus.connect.participant.backend.repository;

import com.campus.connect.participant.backend.model.Competition;
import com.campus.connect.participant.backend.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class EventRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map ResultSet to Event object
    private final class EventRowMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(UUID.fromString(rs.getString("id")));
            event.setName(rs.getString("name"));
            event.setImage(rs.getString("image"));
            event.setFee(rs.getDouble("fee"));
            event.setActivityId(UUID.fromString(rs.getString("activity_id")));
            return event;
        }
    }

    // Fetch all events
    public List<Event> findAll() {
        String sql = "SELECT * FROM event";
        return jdbcTemplate.query(sql, new EventRowMapper());
    }

    public List<Map<String, Object>> findEventsBySocietyId(UUID societyId) {
         
        String sql = "SELECT e.*,a.start_date,a.end_date FROM event e join \"Activities\" a on e.activity_id = a.id where a.society_id = ? "; // Using '?' to safely bind the societyId parameter

        // Execute the query with the societyId parameter and return the list of events
        return jdbcTemplate.queryForList(sql, societyId);
    }

    public Event getDetails(UUID id)
    {
        String sql = "Select * FROM event where id = ?";
        return jdbcTemplate.queryForObject(sql, new EventRowMapper(), id);
    }

}
