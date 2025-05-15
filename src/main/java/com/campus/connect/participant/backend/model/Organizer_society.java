package com.campus.connect.participant.backend.model;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Table("organizer_society")
public class Organizer_society {

    private UUID organizer_id;
    private UUID society_id;

    public UUID getOrganizerId() {
        return organizer_id;
    }
    public UUID getSocietyId() {
        return society_id;
    }

    public void setOrganizerId(UUID organizer_id) {
        this.organizer_id = organizer_id;
    }   
    public void setSocietyId(UUID society_id) {
        this.society_id = society_id;
    }
}
