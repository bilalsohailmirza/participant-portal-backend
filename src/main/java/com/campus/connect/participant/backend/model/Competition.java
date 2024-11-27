package com.campus.connect.participant.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("competition")
public class Competition {

    @Id
    private UUID id;

    private String name;

    private String image;

    private Integer fee;

    private UUID activityId;

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public Integer getFee() {
        return this.fee;
    }

    public UUID getActivityId() {
        return this.activityId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }
}
