package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.columnDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Column;
import com.example.demo.repositories.BoardRepository;
import com.example.demo.repositories.ColumnRepository;

@Service
public class ColumnService {

	@Autowired
	private ColumnRepository repo;

	@Autowired
	private BoardRepository repoBoard;

	public Board getDatasBoard(UUID id) {
		Optional<Board> obj = repoBoard.findById(id);
		return obj.orElseThrow();
	}

	public Column createColumn(columnDTO column, UUID id) {

		Optional<Board> board = repoBoard.findById(id);
		Column obj = new Column(null, column.cor(), column.name(), board.orElseThrow());

		return repo.save(obj);
	}

	public void editColumn(columnDTO column, UUID id) { // id da coluna
		Optional<Column> oldColumn = repo.findById(id);
		oldColumn.orElseThrow().setCor(column.cor() == null ? oldColumn.orElseThrow().getCor() : column.cor());
		oldColumn.orElseThrow().setName(column.name() == null ? oldColumn.orElseThrow().getName() : column.name());
		repo.save(oldColumn.orElseThrow());
	}

	public void delete(UUID id) {
		try {

			repo.deleteById(id);

		} catch (RuntimeException e) {
			throw new RuntimeException();
		}

	}

}
