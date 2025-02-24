package com.campus.connect.participant.backend.repository;

import com.campus.connect.participant.backend.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import com.campus.connect.participant.backend.model.Organizer_society;

@Repository
public class Society_OrganizerRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Organizer_society> rowMapper = new RowMapper<Organizer_society>() {
        @Override
        public Organizer_society mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organizer_society society_organizer = new Organizer_society();
            society_organizer.setOrganizerId(UUID.fromString(rs.getString("organizer_id")));
            society_organizer.setSocietyId(UUID.fromString(rs.getString("society_id")));
            return society_organizer;
        }
    };

    public void InsertSocietyOrganizer(UUID organizer_id, UUID society_id) {
        String sql = "INSERT INTO organizer_society (organizer_id, society_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, organizer_id, society_id);
    }
    

}
