package com.campus.connect.participant.backend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.campus.connect.participant.backend.model.Organizer_team;
import com.campus.connect.participant.backend.model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class OrganizerTeamRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TeamRepository teamRepository;

    private final RowMapper<Organizer_team> organizerTeamRowMapper = new RowMapper<Organizer_team>() {
        @Override
        public Organizer_team mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organizer_team organizerTeam = new Organizer_team();
            organizerTeam.setOrganizerId(UUID.fromString(rs.getString("organizer_id")));
            organizerTeam.setTeamId(UUID.fromString(rs.getString("team_id")));
            return organizerTeam;
        }
    };

    public Organizer_team InsertOrganizerTeam(String teamName, UUID organizerId, UUID societyId)
    {
        Team team = teamRepository.getTeamByName(teamName);

        if(team == null)
        {   
            Team teamTemp = new Team();
            teamTemp.setName(teamName);
            teamTemp.setSocietyId(societyId);
            teamTemp.setId(UUID.randomUUID());

            teamRepository.createTeam(teamTemp);
            team = teamTemp;
        }
        
        String sql = "INSERT INTO organizer_team (organizer_id, team_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, organizerId, team.getId());
        
        Organizer_team organizerTeam = new Organizer_team();
        organizerTeam.setOrganizerId(organizerId);
        organizerTeam.setTeamId(team.getId());
        return organizerTeam;
    }
    
}
