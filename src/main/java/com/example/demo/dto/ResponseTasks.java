package com.example.demo.dto;

import java.util.Set;
import java.util.UUID;

import com.example.demo.entity.SubTasks;

public record ResponseTasks(UUID id, String title, String description, Set<SubTasks> subtasks, Integer pending, Integer feats) {

}
