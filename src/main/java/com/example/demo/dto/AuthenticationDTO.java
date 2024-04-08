package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(@Email(message = "Email format is invalid") String login, @Size(min = 6, max = 32) String password) {

}
