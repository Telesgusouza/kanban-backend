package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.repositories.BoardRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class BoardService {

	@Autowired
	private UserRepository repoUser;
	@Autowired
	private BoardRepository repoBoard;

	public User recoverBoard(User user) {
		Optional<User> obj = repoUser.findById(user.getId());
		return obj.orElseThrow();
	}

	public Board newBoard(Board board) {
		return repoBoard.save(board);
	}

	public void resetNameBoard(String name, UUID id) {

		Optional<Board> obj = this.repoBoard.findById(id);
		obj.orElseThrow().setName(name);
		repoBoard.save(obj.orElseThrow());
	}

	public void delete(UUID id) {
		
		try {
			this.repoBoard.deleteById(id);
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		
	}

}
