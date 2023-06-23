package com.lec.todo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lec.todo.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	
	List<TodoEntity> findByUserId(String userId);
	
	@Query("SELECT t FROM TodoEntity t WHERE t.userId = ?1")
	TodoEntity findByUserIdQuery(String userId);
	
}
