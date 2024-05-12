package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RequestChangeColumnTaskDTO;
import com.example.demo.dto.RequestTaskEdit;
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

	/*
	 * 
	 * 
	 * http://localhost:8080
	 * 
	 * 
	 * token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
	 * eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImRqYW5nb0BnbWFpbC5jb20iLCJleHAiOjE3MjMzMTk2Mjh9
	 * .EarPAWJ7gw0DFTxjhQEbQhKOe_GPDPKV085x7ataLJU
	 * 
	 * 
	 * border 288ebd79-a0d0-4e09-933d-59266bbccc50
	 * 
	 * 
	 * column 5e053fa0-ca46-418a-aa66-0d6837e06cc9
	 * 
	 * 
	 * task c9929a4d-a730-46c9-be6a-f871ddc1aa7c
	 * 
	 * 
	 * 
	 * 
	 */

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

	public Tasks editTasks(UUID id, RequestTaskEdit data) {

		Optional<Tasks> oldTasks = repo.findById(id);

		oldTasks.orElseThrow().setTitle(data.title() == null ? oldTasks.orElseThrow().getTitle() : data.title());

		oldTasks.orElseThrow().setDescription(
				data.description() == null ? oldTasks.orElseThrow().getDescription() : data.description());

		return repo.save(oldTasks.orElseThrow());
	}

	public void delete(UUID id) {
		try {

			repo.deleteById(id);

		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
	}

	public void changeColumnTask(RequestChangeColumnTaskDTO obj) {

		if (obj.idColumn().toString().length() < 1 && obj.idTask().toString().length() < 1) { // invalidFieldException
			throw new InvalidFieldException("Invalid field");
		}

		Tasks task = getTask(obj.idTask());
		Optional<Column> col = repoColumn.findById(obj.idColumn());

		task.setColumn(col.orElseThrow());
		repo.save(task);
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
