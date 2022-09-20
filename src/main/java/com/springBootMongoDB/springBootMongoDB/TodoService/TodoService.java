package com.springBootMongoDB.springBootMongoDB.TodoService;

import com.springBootMongoDB.springBootMongoDB.Exception.TodoException;
import com.springBootMongoDB.springBootMongoDB.model.TodoDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface TodoService {
    public void createTodo(TodoDTO todo) throws TodoException, ConstraintViolationException;
    public List<TodoDTO> getAllTodos();
    public TodoDTO getSingleTodo(String id) throws TodoException;

    public void updateTodo(TodoDTO todo,String id) throws TodoException;

    public void deleteTodo(String id) throws TodoException;
}
