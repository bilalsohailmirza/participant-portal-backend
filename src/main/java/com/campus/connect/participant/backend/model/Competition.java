package com.campus.connect.participant.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Table("competition") // Mapping to the "competition" table
public class Competition {

    @Id
    @Column("id") // Maps to the "id" column
    private UUID id;

    @Column("name") // Maps to the "name" column
    private String name;

    @Column("society_id") // Maps to the "society_id" column
    private UUID societyId;

    @Column("description") // Maps to the "description" column
    private String description;

    @Column("about") // Maps to the "about" column
    private String about;

    @Column("logo") // Maps to the "logo" column
    private String logo;

    @Column("cover") // Maps to the "cover" column
    private String cover;

    @Column("date") // Maps to the "date" column
    private LocalDate date;

    @Column("time") // Maps to the "time" column
    private LocalTime time;

    @Column("rule_book") // Maps to the "rule_book" column
    private String ruleBook;

    @Column("activity_id") // Maps to the "activity_id" column
    private UUID activityId;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getSocietyId() {
        return societyId;
    }

    public void setSocietyId(UUID societyId) {
        this.societyId = societyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getRuleBook() {
        return ruleBook;
    }

    public void setRuleBook(String ruleBook) {
        this.ruleBook = ruleBook;
    }

    public UUID getActivityId() {
        return activityId;
    }

    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }
}
