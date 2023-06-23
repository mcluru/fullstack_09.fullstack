package com.lec.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lec.todo.model.UserEntity;
import com.lec.todo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getUsername() == null) {
			throw new RuntimeException("사용자정보에 오류가 있습니다");
		}
		final String username = userEntity.getUsername();
		if(userRepository.existsByUsername(username)) {
			log.warn("사용자이름이 이미 존재합니다");
			throw new RuntimeException("사용자가 이미 존재합니다");
		}
		return userRepository.save(userEntity);
	}
	
	public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder) {
		
		final UserEntity originalUser = userRepository.findByUsername(username);
		
		// matches메서드를 이용해서 패스워드일치여부 확인
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		else return null;
	}
	
}
