package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SubTasksDTO;
import com.example.demo.dto.TasksDTO;
import com.example.demo.dto.checkboxToggleDTO;
import com.example.demo.entity.Column;
import com.example.demo.entity.SubTasks;
import com.example.demo.entity.Tasks;
import com.example.demo.exceptions.InvalidFieldException;
import com.example.demo.repositories.ColumnRepository;
import com.example.demo.repositories.TasksRepository;
import com.example.demo.repositories.TasksSubTasksRepository;

@Service
public class TasksService {

	@Autowired
	private TasksRepository repo;

	@Autowired
	private TasksSubTasksRepository repoSubTasks;

	@Autowired
	private ColumnRepository repoColumn;

	public Tasks getTask(UUID id) {
		Optional<Tasks> obj = repo.findById(id);
		return obj.orElseThrow(() -> new RuntimeException("task not found"));
	}

	public Tasks updateTask(TasksDTO data, UUID id) {

		if (data.getTitle().length() < 1) {
			throw new InvalidFieldException("Invalid field");
		}

		Optional<Column> column = repoColumn.findById(id);
		Tasks obj = new Tasks(id, data.getTitle(), data.getDescription(), data.getSubTasks(), column.orElseThrow());
		return repo.save(obj);
	}

	public Tasks editTasks(UUID id, TasksDTO data) {

		Optional<Tasks> oldTasks = repo.findById(id);

		oldTasks.orElseThrow().setTitle(data.getTitle() == null ? oldTasks.orElseThrow().getTitle() : data.getTitle());
		oldTasks.orElseThrow().setDescription(
				data.getDescription() == null ? oldTasks.orElseThrow().getDescription() : data.getDescription());
		oldTasks.orElseThrow()
				.setSubtasks(data.getSubTasks() == null ? oldTasks.orElseThrow().getSubtasks() : data.getSubTasks());

		return repo.save(oldTasks.orElseThrow());
	}

	public void delete(UUID id) {
		try {

			repo.deleteById(id);

		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}

	public void deleteSubTask(UUID id) {
		try {

			repoSubTasks.deleteById(id);

		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}

	public SubTasks updateSubTasks(SubTasksDTO data, UUID id) {
		
		if (data.title().length() < 1) {
			throw new InvalidFieldException("Invalid field");
		}
		
		Optional<Tasks> task = repo.findById(id);
		SubTasks obj = new SubTasks(null, false, data.title(), task.orElseThrow());
		return repoSubTasks.save(obj);
	}

	public void checkboxToggle(checkboxToggleDTO data, UUID id) {

		if (data.checkbox() == null) {
			throw new InvalidFieldException("Invalid field");
		}
		
		Optional<SubTasks> obj = repoSubTasks.findById(id);
		obj.orElseThrow().setCheckbox(data.checkbox());
		repoSubTasks.save(obj.orElseThrow());

	}

}
