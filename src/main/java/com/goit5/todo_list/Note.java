package com.goit5.todo_list;

import lombok.Data;

@Data
public class Note {
    private long id;
    private String title;
    private String content;
}
