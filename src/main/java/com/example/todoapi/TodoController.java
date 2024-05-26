package com.example.todoapi;

import lombok.extern.slf4j.Slf4j;
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
    private static final String TODO_NOT_FOUND = "Todo not found";
    private static List<Todo> todoList;

    // No persistent store layer
    // in-server memory, server wiped then data is lost
    public TodoController() {
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "todo-1", 1));
        todoList.add(new Todo(2, true, "todo-2", 2));
    }

    private int fetchTodoIndex(Long todoId) {
        int index = -1;

        for (int i = 0; i < todoList.size(); i++) {
            if (todoList.get(i).getId() == todoId)
                index = i;
        }
        return index;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@RequestParam(required = false, defaultValue = "true") Boolean isCompleted) {
        log.info("Request Params :: isCompleted : {}", isCompleted);
        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodos(@RequestBody Todo newTodo) {
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    // specifying url params
    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId) {
        for (Todo todo : todoList) {
            if (todo.getId() == todoId) {
                return ResponseEntity.status(HttpStatus.OK).body(todo);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long todoId) {
        int index = fetchTodoIndex(todoId);
        if (index == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
        } else {
            todoList.remove(index);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        }
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<?> updateTodoById(
            @PathVariable Long todoId,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer userId)
    {
        logger.info("Request Params :: Id: {}, Completed: {}, Title: {}, UserId: {}", id, completed, title, userId);

        int index = fetchTodoIndex(todoId);

        if(index == -1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
        }

        Todo referredTodo = todoList.get(index);

        if(id != null) referredTodo.setId(id);
        if(completed != null) referredTodo.setCompleted(completed);
        if(title != null) referredTodo.setTitle(title);
        if(userId != null) referredTodo.setUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(referredTodo);
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
}
