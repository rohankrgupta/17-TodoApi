package com.example.todoapi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("realTodo")
public class RealTodoService implements TodoService{
    public void doSomething() {
        System.out.println("Real Todo Service");
    }
}
