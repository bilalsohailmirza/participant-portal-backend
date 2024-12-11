package com.campus.connect.participant.backend.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campus.connect.participant.backend.model.Organizer;
import com.campus.connect.participant.backend.payload.response.OrganizerUserDTO;
import com.campus.connect.participant.backend.repository.OrganizerRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/organizer")
public class OrganizerController {
    
    @Autowired
    private OrganizerRepository organizerRepository;

    @GetMapping("/featuredOrganizers")
    public List<OrganizerUserDTO> getFeaturedOrganizers() {

        return organizerRepository.findByRoleExcomOrPresident();
       
    }
    

}
