package com.company.assignment.chatserver.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User {
    @NotBlank
    @Size(min=3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min=3, max = 20)
    private String lastName;

    @NotBlank
    @Email
    @Size(min=10, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 8)
    private String password;
}
