package com.campus.connect.participant.backend.model;

import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("organizer_team")
public class Organizer_team {
    
    private UUID organizer_id;
    private UUID team_id;

    public UUID getOrganizerId() {
        return organizer_id;
    }
    public UUID getTeamId() {
        return team_id;
    }

    public void setOrganizerId(UUID organizer_id) {
        this.organizer_id = organizer_id;
    }   
    public void setTeamId(UUID team_id) {
        this.team_id = team_id;
    }
}
