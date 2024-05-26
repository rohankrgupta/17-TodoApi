package com.example.todoapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {
    // No persistent store layer
    // in-server memory, server wiped then data is lost
    private static List<Todo> todoList;

    public TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "todo-1", 1));
        todoList.add(new Todo(2, true, "todo-2", 2));
    }
//    Method 1 -> send responses explicitly without ResponseEntity class
//    @GetMapping("/todos")
//    public List<Todo>getTodos(){
//        return todoList;
//    }
//
//    @PostMapping("/todos")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Todo createTodo(@RequestBody Todo newTodo){
//        todoList.add(newTodo);
//        return newTodo;
//    }

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>>getTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodos(@RequestBody Todo newTodo){
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    // specifying path params
    @GetMapping("/todos/{todoId}")
    public ResponseEntity<Todo>getTodoById(@PathVariable Long todoId){
        for(Todo todo : todoList){
            if(todo.getId() == todoId){
                return ResponseEntity.status(HttpStatus.OK).body(todo);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
