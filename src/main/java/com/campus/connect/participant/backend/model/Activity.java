package com.campus.connect.participant.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.OffsetTime;
import java.util.UUID;

@Table("Activities")
public class Activity {

    @Id
    private UUID id;

    private String name;

    private OffsetTime start_date;

    private OffsetTime end_date;

    private UUID societyId;

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public OffsetTime getStartDate() {
        return this.start_date;
    }

    public OffsetTime getEndDate() {
        return this.end_date;
    }

    public UUID getSocietyId() {
        return this.societyId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(OffsetTime startDate) {
        this.start_date = startDate;
    }

    public void setEndDate(OffsetTime endDate) {
        this.end_date = endDate;
    }

    public void setSocietyId(UUID societyId) {
        this.societyId = societyId;
    }
}
