package com.campus.connect.participant.backend.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.campus.connect.participant.backend.model.Team;

@Repository
public class TeamRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Team> teamRowMapper = new RowMapper<Team>() {
        @Override
        public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
            Team team = new Team();
            team.setId(UUID.fromString(rs.getString("id")));
            team.setName(rs.getString("name"));
            team.setSocietyId(UUID.fromString(rs.getString("society_id")));
            return team;
        }
    };

    public Team createTeam(Team team) {
        String sql = "INSERT INTO team (id,name, society_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,team.getId(), team.getName(), team.getSocietyId());
        return team;
    }

    public Team getTeamById(UUID id) {
        String sql = "SELECT * FROM team WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, teamRowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }
    public Team getTeamByName(String teamName)
    {
        String sql = "Select * from team where name = ?";
        try{
            return jdbcTemplate.queryForObject(sql, teamRowMapper, teamName);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public List<Team> getAllTeams() {
        String sql = "SELECT * FROM team";
        return jdbcTemplate.query(sql, teamRowMapper);
    }
}
