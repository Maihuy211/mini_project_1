package com.example.mini_project1.controller;

import com.example.mini_project1.dto.TodoDTO;
import com.example.mini_project1.model.Todo;
import com.example.mini_project1.service.ITodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class TodoCotroller {
    private final ITodoService todoService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("todos", todoService.findAll());
        return "todo-list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("todo", new TodoDTO());
        return "form";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("todo") TodoDTO todoDTO,
                       BindingResult result
    ) {

        if (result.hasErrors()) {
            return "form";
        }
        todoService.save(todoDTO);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        Todo todo = todoService.findById(id);
        if (todo == null) {
            return "redirect:/";
        }

        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setContent(todo.getContent());
        dto.setDueDate(todo.getDueDate());
        dto.setStatus(todo.getStatus());
        dto.setPriority(todo.getPriority());
        model.addAttribute("todo", dto);
        return "form";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        todoService.delete(id);
        return "redirect:/";
    }
}
