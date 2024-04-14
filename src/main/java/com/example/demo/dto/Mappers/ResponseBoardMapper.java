package com.example.demo.dto.Mappers;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.ResponseBoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.User;

public class ResponseBoardMapper {

	public static ResponseBoardDTO toDto(User obj) {

		Set<BoardDTO> list = listBoard(obj.getBoard());

		ResponseBoardDTO newObj = new ResponseBoardDTO(list);

		return newObj;
	}

	public static Set<BoardDTO> listBoard(Set<Board> boards) {

		Set<BoardDTO> list = new HashSet<>();

		for (Board field : boards) {
			list.add(new BoardDTO(field.getId(), field.getName()));
		}

		return list;
	}

}
