package com.campus.connect.participant.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Table("organizer")
public class Organizer {

    @Id
    private UUID id;

    private UUID userId;

    private OffsetDateTime created_at;

    private OffsetDateTime updated_at;

    private String role;

    private String full_name;

    public UUID getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public OffsetDateTime getCreatedAt() {
        return this.created_at;
    }

    public OffsetDateTime getUpdatedAt() {
        return this.updated_at;
    }

    public String getRole() {
        return this.role;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.created_at = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updated_at = updatedAt;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return this.full_name;
    }
    public void setFullName(String full_name) {
        this.full_name = full_name;
    }
}
