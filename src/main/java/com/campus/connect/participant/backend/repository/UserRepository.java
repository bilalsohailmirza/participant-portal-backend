package com.campus.connect.participant.backend.repository;

import com.campus.connect.participant.backend.model.User;
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
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(UUID.fromString(rs.getString("id")));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setOauth(rs.getString("oauth"));  // Update field
            user.setAccessToken(rs.getString("access_token"));  // Update field
            user.setRefreshToken(rs.getString("refresh_token"));  // Update field
            user.setProfilePic(rs.getString("profilepic"));  // Update field
            user.setRole(rs.getString("role"));
            return user;
        }
    };

    // Create a new user
    public User createUser(User user) {
        user.setId(UUID.randomUUID());
        String sql = "INSERT INTO \"user\" (id, email, password, oauth, access_token, refresh_token, profilepic, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getEmail(), user.getPassword(), user.getOauth(),
                user.getAccessToken(), user.getRefreshToken(), user.getProfilePic(), user.getRole());
        return user;
    }

    // Find a user by ID
    public Optional<User> findById(UUID id) {
        String sql = "SELECT * FROM \"user\" WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, id);
        return users.stream().findFirst();
    }

    // Get all users
    public List<User> findAll() {
        String sql = "SELECT * FROM \"user\"";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Update a user by ID
    public int updateUser(UUID id, User user) {
        String sql = "UPDATE \"user\" SET email = ?, password = ?, oauth = ?, access_token = ?, " +
                "refresh_token = ?, profilepic = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getOauth(),
                user.getAccessToken(), user.getRefreshToken(), user.getProfilePic(), user.getRole(), id);
    }

    // Delete a user by ID
    public int deleteUser(UUID id) {
        String sql = "DELETE FROM \"user\" WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Find a user by email
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM \"user\" WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, email);
        return users.stream().findFirst();
    }
}
