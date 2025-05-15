package com.campus.connect.participant.backend.payload.response;

import java.sql.Date;
import java.sql.Time;

public class EventResponse {
    private String eventId;
    private String eventName;
    private String eventDescription;
    private String eventAbout;
    private String eventImage;
    private int eventFee;
    private String eventLogo;
    private String eventCover;
    private Date eventDate;
    private Time eventTime;
    private String eventSocietyId;
    private String eventActivityId;
    private String activityName;

    // Getters and setters

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventAbout() {
        return eventAbout;
    }

    public void setEventAbout(String eventAbout) {
        this.eventAbout = eventAbout;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public int getEventFee() {
        return eventFee;
    }

    public void setEventFee(int eventFee) {
        this.eventFee = eventFee;
    }

    public String getEventLogo() {
        return eventLogo;
    }

    public void setEventLogo(String eventLogo) {
        this.eventLogo = eventLogo;
    }

    public String getEventCover() {
        return eventCover;
    }

    public void setEventCover(String eventCover) {
        this.eventCover = eventCover;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventSocietyId() {
        return eventSocietyId;
    }

    public void setEventSocietyId(String eventSocietyId) {
        this.eventSocietyId = eventSocietyId;
    }

    public String getEventActivityId() {
        return eventActivityId;
    }

    public void setEventActivityId(String eventActivityId) {
        this.eventActivityId = eventActivityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
