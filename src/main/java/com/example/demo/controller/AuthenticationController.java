package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.jwt.TokenService;
import com.example.demo.dto.AuthenticationDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.ResponseToken;
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

	@Operation(summary = "Fazer login", description = "Resurso para fazer login na sua conta", responses = {
			@ApiResponse(responseCode = "201", description = "logado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseToken.class))

			) })
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {

		var usernamePasword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());

		var auth = this.authenticationManager.authenticate(usernamePasword);

		var token = tokenService.generateToken((User) auth.getPrincipal());

		return ResponseEntity.ok(new ResponseToken(token));
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseToken> register(@RequestBody @Valid RegisterDTO data) {

		User user = repo.create(data);
		var token = tokenService.generateToken(user);

		return ResponseEntity.ok().body(new ResponseToken(token));
	}

}
