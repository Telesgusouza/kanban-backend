package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.exceptions.StandardError;
import com.example.demo.dto.RequestBoardDTO;
import com.example.demo.dto.ResponseBoardDTO;
import com.example.demo.dto.ResponseCreateBoard;
import com.example.demo.dto.Mappers.ResponseBoardMapper;
import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Boards", description = "Contem operações relativas ao Board")
@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {

	@Autowired
	private BoardService repo;

	@Operation(summary = "Resgatar dados", description = "Recurso para resgatas dados dos boards e das colunas", responses = {
			@ApiResponse(responseCode = "200", description = "Dados recuperados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBoardDTO.class))), })
	@GetMapping
	public ResponseEntity<ResponseBoardDTO> recoverBoard(@AuthenticationPrincipal User user) {
		User obj = repo.recoverBoard(user);
		return ResponseEntity.status(200).body(ResponseBoardMapper.toDto(obj));
	}

	@Operation(summary = "Criar Board", description = "Recurso para criar os Boards", responses = {
			@ApiResponse(responseCode = "201", description = "Dados recuperados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseCreateBoard.class))),
			@ApiResponse(responseCode = "422", description = "Campo invalido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))) })
	@PostMapping
	public ResponseEntity<ResponseCreateBoard> newBoard(@RequestBody RequestBoardDTO board,
			@AuthenticationPrincipal User user) {

		Board newBoard = new Board(null, board.name(), user);
		Board obj = repo.newBoard(newBoard);

		return ResponseEntity.status(201).body(new ResponseCreateBoard(obj.getId(), obj.getName()));
	}

	@Operation(summary = "Editar Board", description = "Editar o nome do Board", responses = {
			@ApiResponse(responseCode = "204", description = "Editado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "422", description = "Campo invalido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))) })

	@PatchMapping("/{id}")
	public ResponseEntity<Void> resetNameBoard(@RequestBody RequestBoardDTO board, @PathVariable UUID id) {
		repo.resetNameBoard(board.name(), id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Deletar Board", description = "Recurso para deletar o board", responses = {
			@ApiResponse(responseCode = "204", description = "Deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))) })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBoard(@PathVariable UUID id) {

		repo.delete(id);

		return ResponseEntity.noContent().build();
	}

}
