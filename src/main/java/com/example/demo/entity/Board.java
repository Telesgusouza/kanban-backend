package com.example.demo.entity;

import java.io.Serializable;
import java.util.HashSet;
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
@Table(name = "tb_boards")
public class Board implements Serializable {
	private static final long serialVersionUID = -6979071006002877117L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@jakarta.persistence.Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "board")
	private Set<Column> columns = new HashSet<>();

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Board() {
	}

	public Board(UUID id, String name,

			User user) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
	}

	public Board(UUID id, String name,

			Set<Column> columns, User user) {
		super();
		this.id = id;
		this.name = name;
		this.columns = columns;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Column> getColumns() {
		return columns;
	}

	public void setColumns(Set<Column> columns) {
		this.columns = columns;
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
		Board other = (Board) obj;
		return Objects.equals(id, other.id);
	}

}
