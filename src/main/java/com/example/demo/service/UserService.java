package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.Mappers.RegisterMapper;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findByUsername(username);
	}

	public User create(RegisterDTO user) {
		if (this.repo.findByUsername(user.login()) != null) {
			throw new RuntimeException("user already exist");
		} else {

			String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());
			User newUser = RegisterMapper.toUser(user);

			newUser.setPassword(encryptedPassword);

			return repo.save(newUser);
		}

	}

}
