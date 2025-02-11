package com.goit5.todo_list;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "note")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Note {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;
}
