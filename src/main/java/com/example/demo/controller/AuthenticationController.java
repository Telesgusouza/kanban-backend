package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.jwt.TokenService;
import com.example.demo.controller.exceptions.StandardError;
import com.example.demo.dto.AuthenticationDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.ResetPasswordDTO;
import com.example.demo.dto.ResponseBoardDTO;
import com.example.demo.dto.ResponseToken;
import com.example.demo.dto.ResponseUserDTO;
import com.example.demo.dto.Mappers.ResponseBoardMapper;
import com.example.demo.dto.Mappers.ResponseUserMapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Autenticação", description = "Contem operações relativas ao login e cadastro de usuarios")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService repo;

	@Operation(summary = "Login", description = "Resurso para fazer login na sua conta", responses = {
			@ApiResponse(responseCode = "201", description = "logado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseToken.class))

			),

			@ApiResponse(responseCode = "400", description = "Email não existe", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))

			),

			@ApiResponse(responseCode = "422", description = "Campo(s) invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))

			)

	})
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {

		var usernamePasword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());

		var auth = this.authenticationManager.authenticate(usernamePasword);

		var token = tokenService.generateToken((User) auth.getPrincipal());

		return ResponseEntity.ok(new ResponseToken(token));
	}

	@Operation(summary = "Cadastro", description = "Resurso para criar sua conta", responses = {
			@ApiResponse(responseCode = "201", description = "Conta criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseToken.class))

			),

			@ApiResponse(responseCode = "409", description = "Email em uso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))

			),

			@ApiResponse(responseCode = "422", description = "Campo(s) invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))

			)

	})
	@PostMapping("/register")
	public ResponseEntity<ResponseToken> register(@RequestBody @Valid RegisterDTO data) {

		User user = repo.create(data);
		var token = tokenService.generateToken(user);

		return ResponseEntity.ok().body(new ResponseToken(token));
	}

	@Operation(summary = "Redefinir senha", description = "Redefina sua senha", responses = {
			@ApiResponse(responseCode = "204", description = "Modificado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))

			),

			@ApiResponse(responseCode = "422", description = "Campo invalido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))

			)

	})
	@PatchMapping("/resetPasswords")
	public ResponseEntity<Void> redefinePassword(@AuthenticationPrincipal User user,
			@RequestBody ResetPasswordDTO newPassword) {

		User newUser = repo.redefinePassword(user, newPassword);
		return ResponseEntity.status(204).build();
	}

	@Operation(summary = "Deletar", description = "Resurso para deletar a conta", responses = {
			@ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))

			),

			@ApiResponse(responseCode = "422", description = "Erro no login", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))

			),

	})
	@DeleteMapping("/deleteMyAccount")
	public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal User user) {

		repo.deleteAccount(user);
		return ResponseEntity.status(204).build();
	}

	@Operation(summary = "Recuperar dados", description = "Recupere seus dados", responses = {
			@ApiResponse(responseCode = "201", description = "Seus dados recuperados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseUserDTO.class))

			),

	})
	@GetMapping("/recoverData")
	public ResponseEntity<ResponseUserDTO> recoverData(@AuthenticationPrincipal User user) {
		return ResponseEntity.status(201).body(new ResponseUserMapper().toDto(user));
	}

}
