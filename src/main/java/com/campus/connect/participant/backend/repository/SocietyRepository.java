package com.campus.connect.participant.backend.repository;
import com.campus.connect.participant.backend.model.Society;
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
public class SocietyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Society> rowMapper = new RowMapper<Society>(){
        @Override
        public Society mapRow(ResultSet rs, int rowNum) throws SQLException {
            Society society = new Society();
            society.setId(UUID.fromString(rs.getString("id")));
            society.setName(rs.getString("name"));
            society.setImage(rs.getString("image"));
            return society;
        }
     };

     //get all featured societies
     public List<Society> getAllFeaturedSocieties() {
        String sql = "SELECT * FROM \"society\"";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
