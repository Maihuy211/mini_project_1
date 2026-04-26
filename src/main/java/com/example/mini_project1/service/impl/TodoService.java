package com.example.mini_project1.service.impl;

import com.example.mini_project1.dto.TodoDTO;
import com.example.mini_project1.model.Todo;
import com.example.mini_project1.model.typeEnum.Status;
import com.example.mini_project1.repository.ITodoRepository;
import com.example.mini_project1.service.ITodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService implements ITodoService {
    private final ITodoRepository todoRepository;

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo save(TodoDTO todoDTO) {
        Todo todo;
        if(todoDTO.getId() != null){
            todo = todoRepository.findById(todoDTO.getId()).orElse(null);
            if (todo == null) {
                return null;
            }
        }else{
            todo = new Todo();
        }
        todo.setContent(todoDTO.getContent());
        todo.setDueDate(todoDTO.getDueDate());
        todo.setStatus(todoDTO.getStatus() != null ? todoDTO.getStatus() : Status.PENDING);
        todo.setPriority(todoDTO.getPriority());
        return todoRepository.save(todo);
    }

    @Override
    public Todo findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}
