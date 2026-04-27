package com.example.mini_project1.controller;

import com.example.mini_project1.dto.TodoDTO;
import com.example.mini_project1.model.Todo;
import com.example.mini_project1.service.ITodoService;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class TodoCotroller {
    private final ITodoService todoService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @PostMapping("/welcome")
    public String saveOwnerName(@RequestParam("ownerName") String ownerName, 
                                HttpSession session, 
                                RedirectAttributes redirectAttributes) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Tên chủ sở hữu không được để trống!");
            return "redirect:/welcome";
        }
        session.setAttribute("ownerName", ownerName.trim());
        return "redirect:/";
    }

    @GetMapping
    public String home(Model model, HttpSession session) {
        String ownerName = (String) session.getAttribute("ownerName");
        if (ownerName == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("todos", todoService.findAll());
        model.addAttribute("ownerName", ownerName);
        return "todo-list";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session){
        String ownerName = (String) session.getAttribute("ownerName");
        if (ownerName == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("todo", new TodoDTO());
        return "form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model, HttpSession session) {
        String ownerName = (String) session.getAttribute("ownerName");
        if (ownerName == null) {
            return "redirect:/welcome";
        }
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
    @PostMapping("/save")
    public String saveOrUpdate(@Valid @ModelAttribute("todo") TodoDTO todoDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "form";
        }

        String msg = (todoDTO.getId() == null) ? "Thêm mới thành công!" : "Cập nhật thành công!";
        todoService.save(todoDTO);
        redirectAttributes.addFlashAttribute("message", msg);
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id , RedirectAttributes redirectAttributes) {
        todoService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        return "redirect:/";
    }
}
