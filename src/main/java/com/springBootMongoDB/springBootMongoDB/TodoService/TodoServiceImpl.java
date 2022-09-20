package com.springBootMongoDB.springBootMongoDB.TodoService;

import com.springBootMongoDB.springBootMongoDB.Exception.TodoException;
import com.springBootMongoDB.springBootMongoDB.Repositories.TodoRepository;
import com.springBootMongoDB.springBootMongoDB.model.TodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Override
    public void createTodo(TodoDTO todo) throws TodoException, ConstraintViolationException {
        Optional<TodoDTO> optionalTodo=todoRepository.findByTodo(todo.getTodo());
        if(optionalTodo.isPresent()){
            throw new TodoException(TodoException.TodoAlreadyExist());
        }
        else{
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos=todoRepository.findAll();
        if(todos.size()>0){
            return todos;
        }else{
            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoException {
        Optional<TodoDTO> todo=todoRepository.findById(id);
        if(!todo.isPresent()){
            throw new TodoException(TodoException.NotFoundException(id));
        }else{
            return todo.get();
        }
    }

    @Override
    public void updateTodo(TodoDTO todo, String id) throws TodoException {
        Optional<TodoDTO> currTodo= todoRepository.findById(id);
        if(currTodo.isPresent()){
            TodoDTO todoToSave=currTodo.get();
            todoToSave.setCompleted(todo!=null ?todo.isCompleted() : todoToSave.isCompleted());
            todoToSave.setTodo(todo.getTodo()!=null ?todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(todo.getDescription()!=null ?todo.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToSave);
        }else{
            throw  new TodoException(TodoException.NotFoundException(id));
        }
    }

    @Override
    public void deleteTodo(String id) throws TodoException {
        Optional<TodoDTO> todo=todoRepository.findById(id);
        if(!todo.isPresent()){
            throw new TodoException(TodoException.NotFoundException(id));
        }else{
            todoRepository.deleteById(id);
        }
    }
}
