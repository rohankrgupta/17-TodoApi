package com.example.todoapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private static List<Todo> todoList;

//    There are 2 ways to do dependency injection -> 1. Via Autowired (Not recommended) 2. Using Constructor
//    @Autowired
//    @Qualifier("fakeTodo")
    private TodoService todoService;
    private TodoService todoService2;

    public TodoController(
            @Qualifier("fakeTodo") TodoService todoService,
            @Qualifier("realTodo")TodoService todoService2) {
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "todo-1", 1));
        todoList.add(new Todo(2, true, "todo-2", 2));

        // This the manual method of declaring object from the class
        //todoService = new TodoService();

        // Dependency injection in works -> spring injects the bean and specific qualifier mentioned
        this.todoService = todoService;
        this.todoService2 = todoService2;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@RequestParam(required = false, defaultValue = "true") Boolean isCompleted) {
        log.info("Request Params :: isCompleted : {}", isCompleted);
        todoService.doSomething();
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }
}
