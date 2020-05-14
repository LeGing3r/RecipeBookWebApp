package com.example.RecipeBook.dao;

import com.example.RecipeBook.model.TodoItemsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository extends JpaRepository<TodoItemsDto, Integer> {
    @Query("select t from TodoItemsDto t where t.id = 1")
    TodoItemsDto findTodoDto();
}
