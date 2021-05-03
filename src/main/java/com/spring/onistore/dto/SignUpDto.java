package com.spring.onistore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpDto {

    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 36)
    private String userName;
    @NotBlank
    @Size(min = 6, max = 128)
    private String password;

    public SignUpDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

}
