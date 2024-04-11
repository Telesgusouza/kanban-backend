package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordDTO {

	@NotBlank
	@Size(min = 6, max = 32)
	private String newPassword;

	public ResetPasswordDTO() {
	}

	public ResetPasswordDTO(@NotBlank @Size(min = 6, max = 32) String newPassword) {
		super();
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
