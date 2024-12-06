package com.campus.connect.participant.backend.repository;

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
            event.setFee(rs.getInt("fee"));  // Updated: Fee is now Integer in the table
            event.setDescription(rs.getString("description")); // Updated
            event.setAbout(rs.getString("about")); // Updated
            event.setLogo(rs.getString("logo")); // Updated
            event.setCover(rs.getString("cover")); // Updated
            event.setDate(rs.getDate("date").toLocalDate()); // Updated to LocalDate (adjust depending on your date handling)
            event.setTime(rs.getTime("time").toLocalTime()); // Updated to LocalTime
            event.setSocietyId(UUID.fromString(rs.getString("society_id"))); // Updated
            event.setActivityId(UUID.fromString(rs.getString("activity_id"))); // Updated
            return event;
        }
    }

    // Fetch all events
    public List<Event> findAll() {
        String sql = "SELECT * FROM event";
        return jdbcTemplate.query(sql, new EventRowMapper());
    }

    // Fetch events by societyId
    public List<Map<String, Object>> findEventsBySocietyId(UUID societyId) {
        String sql = "SELECT e.* FROM event e " +
                     "JOIN \"Activities\" a ON e.activity_id = a.id WHERE e.society_id = ?"; 
        return jdbcTemplate.queryForList(sql, societyId);
    }

    // Fetch event details by ID
    public Event getDetails(UUID id) {
        String sql = "SELECT * FROM event WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new EventRowMapper(), id);
    }
}
