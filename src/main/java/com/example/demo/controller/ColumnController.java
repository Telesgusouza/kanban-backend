package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.exceptions.StandardError;
import com.example.demo.dto.columnDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Column;
import com.example.demo.service.ColumnService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Columns", description = "Contem todas as operações relativas a colunas")
@RestController
@RequestMapping("/api/v1/columns")
public class ColumnController {

	@Autowired
	private ColumnService repo;

	@Operation(summary = "Recuperar dados", description = "Recurso feito para recuperar dados da coluna (id do board)", responses = {
			@ApiResponse(responseCode = "200", description = "Dados recuperados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Board.class))),

	}

	)
	@GetMapping("/{id}")
	public ResponseEntity<Board> getData(@PathVariable UUID id) {
		Board obj = repo.getDatasBoard(id);
		return ResponseEntity.status(200).body(obj);
	}

	@Operation(summary = "Criar coluna", description = "Recurso para criarmos uma nova coluna (id do board) ", responses = {
			@ApiResponse(responseCode = "201", description = "Coluna criada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Board.class))),
			@ApiResponse(responseCode = "422", description = "Campos invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))

	}

	)
	@PostMapping("/{id}")
	public ResponseEntity<Column> createColumn(@RequestBody columnDTO column, @PathVariable UUID id) {
		Column obj = repo.createColumn(column, id);
		return ResponseEntity.status(201).body(obj);
	}

	@Operation(summary = "Editar coluna", description = "Recurso para editarmos uma coluna (id da coluna)", responses = {
			@ApiResponse(responseCode = "201", description = "Coluna Editada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))), })
	@PutMapping("/{id}")
	public ResponseEntity<Void> editColumn(@RequestBody columnDTO column, @PathVariable UUID id) {
		repo.editColumn(column, id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Deletar coluna", description = "Recurso para deletarmos uma coluna (id da coluna)", responses = {
			@ApiResponse(responseCode = "201", description = "Coluna Deletada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))), })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteColumn(@PathVariable UUID id) {

		repo.delete(id);
		return ResponseEntity.noContent().build();
	}

}
