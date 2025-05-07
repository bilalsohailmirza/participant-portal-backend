package com.campus.connect.participant.backend.payload.request;
import java.util.UUID;

public class CreateTeamRequest {
    private String teamName;
    private UUID societyId;

    // Getters and Setters
    public String getTeamName() {
        return teamName;
    }
    public void setGetTeamName(String teamName) {
        this.teamName = teamName;
    }
    public UUID getSocietyId() {
        return societyId;
    }
    public void setGetSocietyId(UUID societyId) {
        this.societyId = societyId;
    }
}
