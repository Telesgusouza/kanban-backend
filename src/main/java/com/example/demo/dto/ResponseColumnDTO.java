package com.example.demo.dto;

import java.util.Set;
import java.util.UUID;

public record ResponseColumnDTO(UUID id, String name, Set<ColumnDTO> columns) {

}
