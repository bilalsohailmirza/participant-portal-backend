package com.campus.connect.participant.backend.controller;
import com.campus.connect.participant.backend.model.Society;
import com.campus.connect.participant.backend.repository.SocietyRepository;
import com.campus.connect.participant.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/societies")
public class SocietyController {

    @Autowired
    private SocietyRepository societyRepository;

    @GetMapping("/get-featured-societies")
    public List<Society> getFeaturedSocieties() {
        return societyRepository.getAllFeaturedSocieties();
    }
    
}