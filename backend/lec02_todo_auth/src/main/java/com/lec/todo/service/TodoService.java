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
									  .title("������ ���� 1").build();
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
		log.info("Entity Id ; {} ���� ����", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	
	
	public void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Todo(entity)�� null�Դϴ�. �ٽ� �Է��Ͻÿ�");
			throw new RuntimeException("Todo(entity)�� null�Դϴ�");
		} 
		
		if(entity.getUserId() == null) {
			log.warn("�����ID�� null�Դϴ�. �����ID�� �Է��Ͻÿ�");
			throw new RuntimeException("�����ID�� null�Դϴ�");
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
			log.error("{} �ڷḦ ã�� ���߽��ϴ�", entity.getId(), e);
			throw new RuntimeException(entity.getId() + "�ڷ��������");
		}
		return retrive(entity.getUserId());
		
	}
	
}