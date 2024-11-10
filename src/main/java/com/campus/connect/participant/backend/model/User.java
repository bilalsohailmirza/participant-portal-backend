package com.campus.connect.participant.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("user")
public class User {
        @Id
        private UUID id;
        private String email;
        private String password;
        private String oAuth;
        private String accessToken;
        private String refreshToken;
        private String profilePicture;
        private String role;



        // Getters and setters
        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getAccessToken() {
                return accessToken;
        }

        public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
        }

        public String getRefreshToken() {
                return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
                this.refreshToken = refreshToken;
        }

        public String getProfilePicture() {
                return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
                this.profilePicture = profilePicture;
        }

        public String getRole() {
                return role;
        }

        public void setRole(String role) {
                this.role = role;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getOAuth() {
                return oAuth;
        }

        public void setOAuth(String oAuth) {
                this.oAuth = oAuth;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}
