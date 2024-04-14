package com.example.demo.dto;

import java.util.Set;

import com.example.demo.entity.Board;

public record ResponseBoardDTO(Set<BoardDTO> board) {

}
