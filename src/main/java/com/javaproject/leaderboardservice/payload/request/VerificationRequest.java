package com.javaproject.leaderboardservice.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VerificationRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    public Integer getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(Integer verification_code) {
        this.verification_code = verification_code;
    }

    private Integer verification_code;

    private String password;
    private String username;
    private boolean is_verified;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
