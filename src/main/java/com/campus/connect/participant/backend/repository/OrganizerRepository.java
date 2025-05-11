package com.campus.connect.participant.backend.repository;

import com.campus.connect.participant.backend.model.Event;
import com.campus.connect.participant.backend.model.Organizer;
import com.campus.connect.participant.backend.payload.response.OrganizerUserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDate;
@Repository
public class OrganizerRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

        private final RowMapper<Organizer> rowMapper = new RowMapper<Organizer>() {
        @Override
        public Organizer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organizer organizer = new Organizer();
            organizer.setId(UUID.fromString(rs.getString("id")));
            organizer.setUserId(UUID.fromString(rs.getString("user_id")));
            organizer.setCreatedAt(rs.getObject("created_at", java.time.OffsetDateTime.class));
            organizer.setUpdatedAt(rs.getObject("updated_at", java.time.OffsetDateTime.class));
            organizer.setRole(rs.getString("role"));
            organizer.setFullName(rs.getString("full_name"));
            return organizer;

        }
    };

       public List<OrganizerUserDTO> findByRoleExcomOrPresident() {
        String sql = """
                SELECT o.id AS organizer_id,
                       o.user_id AS user_id,
                       o.created_at AS created_at,
                       o.updated_at AS updated_at,
                       o.role AS role,
                       u.email AS user_email,
                       u.profilepic AS profilepic
                FROM organizer o
                INNER JOIN "user" u ON o.user_id = u.id
                WHERE o.role IN ('Excom', 'President');
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapToOrganizerUserDTO(rs));
    }

    private OrganizerUserDTO mapToOrganizerUserDTO(ResultSet rs) {
        try {
            OrganizerUserDTO dto = new OrganizerUserDTO();
            dto.setOrganizerId(UUID.fromString(rs.getString("organizer_id")));
            dto.setUserId(UUID.fromString(rs.getString("user_id")));
            dto.setCreatedAt(rs.getObject("created_at", java.time.OffsetDateTime.class));
            dto.setUpdatedAt(rs.getObject("updated_at", java.time.OffsetDateTime.class));
            dto.setRole(rs.getString("role"));
            dto.setUserEmail(rs.getString("user_email"));
            dto.setProfilePic(rs.getString("profilepic"));
            return dto;
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping result set to OrganizerUserDTO", e);
        }
    }

    public Organizer createOrganizer(Organizer organizer)
    {
        organizer.setId(UUID.randomUUID());
        String sql = "INSERT INTO organizer (id, user_Id, role, created_at, updated_at, full_name)" + "VALUES (?,?,?,?,?,?)";

        jdbcTemplate.update(sql, organizer.getId(), organizer.getUserId(), organizer.getRole(),LocalDate.now(),LocalDate.now(),organizer.getFullName());
        return organizer;
    }
    public Organizer getOrganizerByUserId(UUID userId){
        String sql = "SELECT * FROM organizer WHERE user_id = ? LIMIT 1";
        Organizer organizer = jdbcTemplate.queryForObject(sql, rowMapper, userId);
        return organizer;
    }

    public List<Organizer> getOrganizerBySocietyId(UUID societyId)
    {
        String sql = "select * from organizer o join organizer_society s on o.id = s.organizer_id where society_id = ?";
        List<Organizer> organizer = jdbcTemplate.query(sql, rowMapper, societyId);
        return organizer;
    }

    public UUID getOrganizerIdByEmail(String email) {
        String sql = "SELECT o.id FROM organizer o JOIN \"user\" u ON u.id = o.user_id WHERE u.email = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> UUID.fromString(rs.getString("id")), email);
    }
}
