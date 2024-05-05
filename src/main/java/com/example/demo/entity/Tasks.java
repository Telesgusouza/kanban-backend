package com.example.demo.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tasks")
public class Tasks implements Serializable {
	private static final long serialVersionUID = 8553564998957528858L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String title;

	private String description;

	@OneToMany(mappedBy = "tasks")
	private Set<SubTasks> subtasks;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "column_id")
	private Column column;

	public Tasks() {
	}

	public Tasks(UUID id, String title, String description, Set<SubTasks> subtasks, Column column) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.subtasks = subtasks;
		this.column = column;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Set<SubTasks> getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(Set<SubTasks> subtasks) {
		this.subtasks = subtasks;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tasks other = (Tasks) obj;
		return Objects.equals(id, other.id);
	}

}
