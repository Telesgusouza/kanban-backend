package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.exceptions.StandardError;
import com.example.demo.dto.SubTasksDTO;
import com.example.demo.dto.TasksDTO;
import com.example.demo.dto.checkboxToggleDTO;
import com.example.demo.entity.SubTasks;
import com.example.demo.entity.Tasks;
import com.example.demo.service.TasksService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tasks", description = "Contem todas as operações relativas a tasks e subtasks")
@RestController
@RequestMapping("/api/v1/tasks")
public class TasksController {

	@Autowired
	private TasksService repo;

	@Operation(summary = "Buscar Task", description = "Busque por uma task especifica", responses = {

			@ApiResponse(responseCode = "200", description = "Task recuperada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tasks.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<Tasks> getTask(@PathVariable UUID id) {
		Tasks task = repo.getTask(id);
		return ResponseEntity.status(200).body(task);
	}

	@Operation(summary = "Nova Task", description = "Crie uma nova Task", responses = {

			@ApiResponse(responseCode = "201", description = "Task criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tasks.class))),
			@ApiResponse(responseCode = "422", description = "Campos invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))

	})
	@PostMapping("/{id}")
	public ResponseEntity<Tasks> updateTask(@RequestBody TasksDTO data, @PathVariable UUID id) { // id columns
		Tasks obj = repo.updateTask(data, id);
		return ResponseEntity.status(201).body(obj);
	}

	@Operation(summary = "Editar Task", description = "Edite uma task", responses = {

			@ApiResponse(responseCode = "201", description = "Task editada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tasks.class))) })
	@PutMapping("/{id}")
	public ResponseEntity<Tasks> editTasks(@PathVariable UUID id, @RequestBody TasksDTO data) {
		Tasks obj = repo.editTasks(id, data);
		return ResponseEntity.status(201).body(obj);
	}

	@Operation(summary = "Deletar Task", description = "Delete uma task", responses = {

			@ApiResponse(responseCode = "200", description = "Task Deletada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTasks(@PathVariable UUID id) {
		repo.delete(id);
		return ResponseEntity.noContent().build();
	}

	// subTasks
	@Operation(summary = "Adicionar subtask", description = "Criar uma subtask de uma task", responses = {

			@ApiResponse(responseCode = "200", description = "Subtask criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubTasks.class))),

			@ApiResponse(responseCode = "422", description = "Campos invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))

	})
	@PostMapping("/subtasks/{id}")
	public ResponseEntity<SubTasks> updateSubTasks(@PathVariable UUID id, @RequestBody SubTasksDTO data) {
		SubTasks obj = repo.updateSubTasks(data, id);
		return ResponseEntity.status(200).body(obj);
	}

	@Operation(summary = "Estado Checkbox", description = "Mudar estado da checkbox de uma subtask", responses = {

			@ApiResponse(responseCode = "200", description = "Subtask criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubTasks.class))),

			@ApiResponse(responseCode = "422", description = "Campos invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))

	})

	@PatchMapping("/subtasks/{id}")
	public ResponseEntity<Void> checkboxToggle(@PathVariable UUID id, @RequestBody checkboxToggleDTO data) {
		repo.checkboxToggle(data, id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Deletar subtask", description = "Deletar uma subtask de uma task", responses = {

			@ApiResponse(responseCode = "204", description = "Subtask deletada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubTasks.class))),

	})

	@DeleteMapping("/subtasks/{id}")
	public ResponseEntity<Void> deleteSubTask(@PathVariable UUID id) {
		repo.deleteSubTask(id);
		return ResponseEntity.noContent().build();
	}

}
