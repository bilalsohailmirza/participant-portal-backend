package com.campus.connect.participant.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("team")
public class Team {

    @Id
    private UUID id;

    private UUID societyId;

    private String name;

    public UUID getId() {
        return this.id;
    }

    public UUID getSocietyId() {
        return this.societyId;
    }

    public String getName() {
        return this.name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSocietyId(UUID societyId) {
        this.societyId = societyId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
