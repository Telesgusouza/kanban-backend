package com.example.demo.dto.Mappers;

import com.example.demo.dto.AuthenticationDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.UserRole;

public class LoginMapper {

	public static User toUser(AuthenticationDTO dto) {
		User user = new User(null, dto.login(), dto.password(), UserRole.USER);
		return user;
	}

}
