package com.goit5.todo_list;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "note")
@Entity
@Data
public class Note {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;
}
