package com.campus.connect.participant.backend.repository;

import com.campus.connect.participant.backend.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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


}
