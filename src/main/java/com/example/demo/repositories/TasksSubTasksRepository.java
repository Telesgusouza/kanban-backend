package com.example.demo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SubTasks;

public interface TasksSubTasksRepository extends JpaRepository<SubTasks, UUID> {

}
