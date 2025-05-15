package com.campus.connect.participant.backend.controller;

import com.campus.connect.participant.backend.model.Team;
import com.campus.connect.participant.backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.campus.connect.participant.backend.payload.request.CreateTeamRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    // Get all teams
    @GetMapping("/all-teams")
    public List<Team> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    // Get team by id
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable UUID id) {
        Team team = teamRepository.getTeamById(id);
        return team != null ? ResponseEntity.ok(team) : ResponseEntity.notFound().build();
    }

    // Create new team
    @PostMapping("/create-team")
    public Team createTeam(@RequestBody CreateTeamRequest  request) {
        System.out.println(request);
        Team team = new Team();
        team.setId(UUID.randomUUID());
        team.setName(request.getTeamName());
        team.setSocietyId(request.getSocietyId());
        return teamRepository.createTeam(team);
    }



    
}