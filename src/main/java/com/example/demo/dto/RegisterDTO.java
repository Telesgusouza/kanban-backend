package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {

//	@NotBlank
	@Email(message = "Email format is invalid")
	private String login;

//	@NotBlank
	@Size(min = 6, max = 32)
	private String password;

	public RegisterDTO() {
		super();
	}

	public RegisterDTO(@NotBlank @Email(message = "Email format is invalid") String login,
			@NotBlank @Size(min = 6, max = 32) String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
