package com.example.demo.dto.Mappers;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.UserRole;

public class RegisterMapper {

	public static User toUser(RegisterDTO dto) {
		User user = new User(null, dto.getLogin(), dto.getPassword(), UserRole.USER);
		return user;
	}
}
