package com.lec.todo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lec.todo.dto.ResponseDTO;
import com.lec.todo.dto.TodoDTO;
import com.lec.todo.model.TodoEntity;
import com.lec.todo.service.TodoService;

@RestController 
@RequestMapping("/todo")
public class TodoController {

	@Autowired 
	private TodoService service;
	
	@GetMapping("/test") // http://localhost:8080/todo/test
	public ResponseEntity<?> testTodo() {
		String title = service.testService();
		List<String> list = new ArrayList<>();
		list.add(title);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping
	public ResponseEntity<?> retriveTodoList() {
		String tempUserId = "hong";
		List<TodoEntity> entities = service.retrive(tempUserId);
		List<TodoDTO> list = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping()
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
		try {
			String tempUserId = "hong";
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			entity.setId(null);
			entity.setUserId(tempUserId);
			List<TodoEntity> entities = service.create(entity);
			List<TodoDTO> list = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(list).build();
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping()
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
		String tempUserId = "hong";
		TodoEntity entity = TodoDTO.toEntity(dto);
		entity.setUserId(tempUserId);
		List<TodoEntity> entities = service.update(entity);
		List<TodoDTO> list = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}	
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
		try {
			String tempUserId = "hong";
			TodoEntity entity = TodoDTO.toEntity(dto);
			entity.setUserId(tempUserId);
			List<TodoEntity> entities = service.delete(entity);			
			List<TodoDTO> list = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(list).build();			
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);			
		}
	}
}