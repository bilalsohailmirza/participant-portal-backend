package com.campus.connect.participant.backend.payload.request;

public class SignupRequest {
    private String email;
    private String password;
    private String role;
    private String organizer_role;
    private String society_name;
    private String team_name;
    private String full_name;
    // Getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }
    public void setRole(String role)
    {
        this.role = role;
    }

    public String getOrganizer_role()
    {
        return organizer_role;
    }
    public void setOrganizer_role(String organizer_role)
    {
        this.organizer_role = organizer_role;
    }
    public void setSociety_name(String society_name)
    {
        this.society_name = society_name;
    }
    public String getSociety_name()
    {   
        System.out.println("Society Name: "+society_name);
        return society_name;
    }
    public void setTeam_name(String team_name)
    {
        this.team_name = team_name;
    }
    public String getTeam_name()
    {   
        return team_name;
    }

    public String getFull_name()
    {
        return full_name;
    }

    public void setFull_name(String full_name)
    {
        this.full_name = full_name;
    }
}
