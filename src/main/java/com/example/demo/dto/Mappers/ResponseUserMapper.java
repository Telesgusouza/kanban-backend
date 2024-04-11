package com.example.demo.dto.Mappers;

import com.example.demo.dto.ResponseUserDTO;
import com.example.demo.entity.User;

public class ResponseUserMapper {

	public static ResponseUserDTO toDto(User dto) {
		ResponseUserDTO user = new ResponseUserDTO(dto.getUsername(), dto.getRole());
		return user;
	}

}
