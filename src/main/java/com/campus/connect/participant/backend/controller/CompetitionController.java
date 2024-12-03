package com.campus.connect.participant.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campus.connect.participant.backend.model.Competition;
import com.campus.connect.participant.backend.model.Event;
import com.campus.connect.participant.backend.repository.CompetitionRepository;

@RestController
@RequestMapping("/api/competition")
public class CompetitionController {
    
    @Autowired 
    private CompetitionRepository competitionRepository;

    
     @GetMapping("/getCompetitionBySocietyId")
    public List<Map<String, Object>> getCompetitionsBySociety(@RequestParam UUID societyId) {
        return competitionRepository.findCompetitionsBySocietyId(societyId);
    }

    @GetMapping("/getDetails")
    public Competition getDetails(@RequestParam UUID id) {

        return competitionRepository.getDetails(id);
        
    }
    
}
