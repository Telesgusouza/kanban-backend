package com.example.demo.dto;

import java.util.Set;
import java.util.UUID;

public record ColumnDTO(UUID id, String cor, String name, Set<TasksColumnDTO> tasks) {

}
