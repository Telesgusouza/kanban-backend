package com.example.demo.dto;

import java.util.Set;

import com.example.demo.entity.Board;
import com.example.demo.entity.enums.UserRole;

public record ResponseUserDTO(String username, UserRole role) {

}
