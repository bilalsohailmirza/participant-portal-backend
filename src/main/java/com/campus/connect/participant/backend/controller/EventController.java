package com.campus.connect.participant.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campus.connect.participant.backend.repository.EventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.campus.connect.participant.backend.model.Event;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/featured-events")
    public List<Event> getAllFeaturedEvents() {
        return eventRepository.findAll();
    }

     @GetMapping("/getEventBySocietyId")
    public List<Event> getEventsBySociety(@RequestParam UUID societyId) {
        return eventRepository.findEventsBySocietyId(societyId);
    }
    
}
