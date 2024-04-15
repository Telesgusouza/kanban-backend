package com.example.demo.entity;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_subtasks")
public class SubTasks implements Serializable {
	private static final long serialVersionUID = 2433310364026650258L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private Boolean checkbox;
	private String title;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "tasks_id")
	private Tasks tasks;

	public SubTasks() {
	}

	public SubTasks(UUID id, Boolean checkbox, String title, Tasks tasks) {
		super();
		this.id = id;
		this.checkbox = checkbox;
		this.title = title;
		this.tasks = tasks;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Boolean getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(Boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
