package com.campus.connect.participant.backend.payload.response;

import java.sql.Date;
import java.sql.Time;

public class CompetitionResponse {
    private String competitionId;
    private String competitionName;
    private String competitionDescription;
    private Date competitionDate;
    private Time competitionTime;
    private String competitionRuleBook;
    private String activityId;
    private String activityName;

    // Getter and Setter for competitionId
    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    // Getter and Setter for competitionName
    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    // Getter and Setter for competitionDescription
    public String getCompetitionDescription() {
        return competitionDescription;
    }

    public void setCompetitionDescription(String competitionDescription) {
        this.competitionDescription = competitionDescription;
    }

    // Getter and Setter for competitionDate
    public Date getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(Date competitionDate) {
        this.competitionDate = competitionDate;
    }

    // Getter and Setter for competitionTime
    public Time getCompetitionTime() {
        return competitionTime;
    }

    public void setCompetitionTime(Time competitionTime) {
        this.competitionTime = competitionTime;
    }

    // Getter and Setter for competitionRuleBook
    public String getCompetitionRuleBook() {
        return competitionRuleBook;
    }

    public void setCompetitionRuleBook(String competitionRuleBook) {
        this.competitionRuleBook = competitionRuleBook;
    }

    // Getter and Setter for activityId
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    // Getter and Setter for activityName
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
