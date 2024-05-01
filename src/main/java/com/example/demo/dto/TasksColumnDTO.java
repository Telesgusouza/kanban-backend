package com.example.demo.dto;

import java.util.UUID;

public record TasksColumnDTO(UUID id, String title, Integer pending, Integer feats) {

}
