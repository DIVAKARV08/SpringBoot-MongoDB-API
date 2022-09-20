package com.springBootMongoDB.springBootMongoDB.Controllers;

import com.springBootMongoDB.springBootMongoDB.Exception.TodoException;
import com.springBootMongoDB.springBootMongoDB.Repositories.TodoRepository;
import com.springBootMongoDB.springBootMongoDB.TodoService.TodoService;
import com.springBootMongoDB.springBootMongoDB.model.TodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDTO> todos= todoService.getAllTodos();
        return new ResponseEntity<>(todos, todos.size()>0? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
        try {
            todoService.createTodo(todo);
            return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable String id){
        try {
            return new ResponseEntity<>(todoService.getSingleTodo(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String id,@RequestBody TodoDTO todo){
        try{
            todoService.updateTodo(todo,id);
            return new ResponseEntity<>("Updated todo with "+id,HttpStatus.OK);
        }catch (TodoException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteSingleTodo(@PathVariable String id){
        try{
            todoService.deleteTodo(id);
            return new ResponseEntity<>("todo is deleted successfully",HttpStatus.OK);
        }catch (TodoException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
