package com.example.mini_project1.service;

import com.example.mini_project1.dto.TodoDTO;
import com.example.mini_project1.model.Todo;
import java.util.List;

public interface ITodoService {
    List<Todo> findAll();
    Todo save(TodoDTO todoDTO);
    Todo findById(Long id);
    void delete(Long id);
}
