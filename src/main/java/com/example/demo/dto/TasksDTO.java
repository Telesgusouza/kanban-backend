package com.example.demo.dto;

import java.util.Set;

import com.example.demo.entity.SubTasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TasksDTO {

	@NotBlank
	@Size(min = 4)
	private String title;
	private String description;
	private Set<SubTasks> subTasks;

	public TasksDTO() {
	}

	public TasksDTO(@NotBlank String title, String description, Set<SubTasks> subTasks) {
		super();
		this.title = title;
		this.description = description;
		this.subTasks = subTasks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<SubTasks> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(Set<SubTasks> subTasks) {
		this.subTasks = subTasks;
	}

}
