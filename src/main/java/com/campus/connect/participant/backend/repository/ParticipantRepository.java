package com.campus.connect.participant.backend.repository;

import com.campus.connect.participant.backend.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ParticipantRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper for the Participant entity
    private final RowMapper<Participant> participantRowMapper = (ResultSet rs, int rowNum) -> {
        Participant participant = new Participant();
        participant.setId(UUID.fromString(rs.getString("id")));
        participant.setUserId(UUID.fromString(rs.getString("user_id")));
        participant.setFullName(rs.getString("full_name"));
        participant.setEmail(rs.getString("email"));
        participant.setPhone(rs.getString("phone"));
        participant.setStudent(rs.getBoolean("student"));
        participant.setOrganization(rs.getString("organization"));
        return participant;
    };

    // Create a new participant
    public boolean createOneParticipant(Participant participant) {
        String sql = "INSERT INTO participant (id, user_id, full_name, email, phone, student, organization) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            sql,
            participant.getId(),
            participant.getUserId(),
            participant.getFullName(),
            participant.getEmail(),
            participant.getPhone(),
            participant.isStudent(),
            participant.getOrganization()
        );
        return true;
    }

    // Find a participant by ID
    public Optional<Participant> findById(UUID id) {
        String sql = "SELECT * FROM participant WHERE id = ?";
        List<Participant> participants = jdbcTemplate.query(sql, participantRowMapper, id);
        return participants.stream().findFirst();
    }

    // Find all participants
    public List<Participant> findAll() {
        String sql = "SELECT * FROM participant";
        return jdbcTemplate.query(sql, participantRowMapper);
    }

    // Update a participant by ID
    public int updateParticipant(UUID id, Participant participant) {
        String sql = "UPDATE participant SET full_name = ?, email = ?, phone = ?, student = ?, organization = ? " +
                     "WHERE id = ?";
        return jdbcTemplate.update(
            sql,
            participant.getFullName(),
            participant.getEmail(),
            participant.getPhone(),
            participant.isStudent(),
            participant.getOrganization(),
            id
        );
    }

    // Delete a participant by ID
    public int deleteParticipant(UUID id) {
        String sql = "DELETE FROM participant WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Associate a participant with an activity
    public int associateWithActivity(UUID participantId, UUID activityId) {
        System.out.println(activityId);
        String sql = "INSERT INTO \"participant_Activities\" (participant_id, activity_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, participantId, activityId);
    }

    public Optional<Participant> getParticipantByUserId(UUID user_id)
    {
        String sql = "Select * from \"participant\" where user_id = ?";
        List<Participant> users = jdbcTemplate.query(sql, participantRowMapper, user_id);
        return users.stream().findFirst();
    }
}   
 
