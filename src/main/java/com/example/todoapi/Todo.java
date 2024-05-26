package com.example.todoapi;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Todo {

    public Todo(int id, boolean completed, String title, int userId){
        setId(id);
        setCompleted(completed);
        setTitle(title);
        setUserId(userId);
    }

    private int id;
    private boolean completed;
    private String title;
    private int userId;
}
