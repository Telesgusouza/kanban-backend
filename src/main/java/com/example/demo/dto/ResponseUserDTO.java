package com.example.demo.dto;

import com.example.demo.entity.enums.UserRole;

public record ResponseUserDTO(String username, UserRole role) {

}
