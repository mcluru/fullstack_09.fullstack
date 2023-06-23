package com.lec.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ranges.RangeException;

import com.lec.todo.model.TodoEntity;
import com.lec.todo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {

	@Autowired
	TodoRepository repository;
	
	public String testService() {
		TodoEntity entity = TodoEntity.builder().title("오늘의 할일 1").build();
		repository.save(entity);		
		TodoEntity saveEntity = repository.findById(entity.getId()).get();
		return saveEntity.toString();
	}
	
	public List<TodoEntity> retrive(final String userId) {
		return repository.findByUserId(userId);
	}
	
	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity);
		repository.save(entity);
		log.info("Entity Id : {}가 성공적으로 저장되었습니다!", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	
	public void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Todo(entity)가 null입니다! 데이터를 입력하세요");
			throw new RuntimeException("Todo(entity)가 null입니다!");
		} 
		
		if(entity.getUserId() == null) {
			log.warn("사용자ID가 null입니다! 사용자ID를 입력하세요");
			throw new RuntimeException("사용자ID가 null입니다");
		}
	}
	
	public List<TodoEntity> update(final TodoEntity entity) {
		validate(entity);
		final Optional<TodoEntity> orginal = repository.findById(entity.getId());
		
		orginal.ifPresent(todo -> {
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
			log.error("{} id자료를 찾지 못했습니다!", entity.getId(), e);
			throw new RuntimeException(entity.getId() + "자료삭제실패!!!");
		}
		return retrive(entity.getUserId());
	}
	
	
	
	
}
