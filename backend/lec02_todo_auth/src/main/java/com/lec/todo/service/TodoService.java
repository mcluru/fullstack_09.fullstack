package com.lec.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.lec.todo.model.TodoEntity;
import com.lec.todo.persistence.TodoRepository;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j	//
@Service
public class TodoService {
	
	@Autowired
	TodoRepository repository;
	
	public String testService() {
		TodoEntity entity = TodoEntity.builder()
									  .title("오늘의 할일 1").build();
		repository.save(entity);
		
		System.out.println(entity.getId());
		
		TodoEntity saveEntity = repository.findById(entity.getId()).get();
		return saveEntity.toString();
	}
	
	
	public List<TodoEntity> retrive(final String userId) {
		return repository.findByUserId(userId);
	}
	
	
	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity);
		repository.save(entity);
		log.info("Entity Id ; {} 저장 성공", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	
	
	public void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Todo(entity)가 null입니다. 다시 입력하시오");
			throw new RuntimeException("Todo(entity)가 null입니다");
		} 
		
		if(entity.getUserId() == null) {
			log.warn("사용자ID가 null입니다. 사용자ID를 입력하시오");
			throw new RuntimeException("사용자ID가 null입니다");
		}
		
	}
	
	
	public List<TodoEntity> update(final TodoEntity entity) {
		validate(entity);
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			repository.save(todo);
		});
		
		return retrive(entity.getUserId());
	}
	
	public List<TodoEntity> delete(final TodoEntity entity) {
		validate(entity);
		try {
			repository.delete(entity);
		} catch (Exception e) {
			log.error("{} 자료를 찾지 못했습니다", entity.getId(), e);
			throw new RuntimeException(entity.getId() + "자료삭제실패");
		}
		return retrive(entity.getUserId());
		
	}
	
}
