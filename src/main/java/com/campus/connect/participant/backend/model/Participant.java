package com.campus.connect.participant.backend.model;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Table("participant")
public class Participant {

    @Id
    private UUID id; // Maps to the 'id' column in the DB

    private UUID userId; // Maps to the 'user_id' column in the DB

    private String fullName; // Maps to the 'full_name' column in the DB

    private String email; // Maps to the 'email' column in the DB

    private String phone; // Maps to the 'phone' column in the DB

    private boolean student; // Maps to the 'student' column in the DB

    private String organization; // Maps to the 'organization' column in the DB

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
