package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.ResetPasswordDTO;
import com.example.demo.dto.Mappers.RegisterMapper;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ExistUserException;
import com.example.demo.exceptions.InvalidFieldException;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (this.repo.findByUsername(username) == null) {
			throw new UserDoesNotExistException("Email does not exist");
		}

		return repo.findByUsername(username);
	}

	@Transactional()
	public User create(RegisterDTO user) {
		if (this.repo.findByUsername(user.getLogin()) != null) {
			throw new ExistUserException("user already exist");
		} else {

			String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
			User newUser = RegisterMapper.toUser(user);

			newUser.setPassword(encryptedPassword);

			return repo.save(newUser);

		}
	}

	public User redefinePassword(User user, ResetPasswordDTO newPassword) {
		if (newPassword.getNewPassword().length() < 6 || newPassword.getNewPassword().length() > 32) {
			throw new InvalidFieldException("Field value exceeds size");
		}
		;

		String password = new BCryptPasswordEncoder().encode(newPassword.getNewPassword());
		user.setPassword(password);
		return repo.save(user);
	}

	public void deleteAccount(User user) {

		if (user == null) {
			System.out.println("=============================");
			System.out.println("Erro chegou aqui");
		}

		try {

			repo.deleteById(user.getId());
		} catch (RuntimeException e) {
			throw new ExistUserException("user already exist");
		}

	}

}
