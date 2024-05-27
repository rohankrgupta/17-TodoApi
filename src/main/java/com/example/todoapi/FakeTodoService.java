package com.example.todoapi;
import org.springframework.stereotype.Service;

@Service("fakeTodo")
public class FakeTodoService implements TodoService {
    public void doSomething(){
        System.out.println("Fake Todo Service");
    }
}