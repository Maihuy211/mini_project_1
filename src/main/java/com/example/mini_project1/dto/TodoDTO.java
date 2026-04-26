package com.example.mini_project1.dto;

import com.example.mini_project1.model.typeEnum.Priority;
import com.example.mini_project1.model.typeEnum.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDTO {
    private Long id;
    
    @NotBlank(message = "Task content cannot be empty")
    private String content;
    
    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;


    @NotNull(message = "Status cannot be empty")
    private Status status;

    @NotNull(message = "Priority cannot be empty")
    private Priority priority;
}
