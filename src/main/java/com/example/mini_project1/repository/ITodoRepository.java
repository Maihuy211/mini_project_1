package com.example.mini_project1.repository;

import com.example.mini_project1.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITodoRepository extends JpaRepository<Todo,Long> {
}
