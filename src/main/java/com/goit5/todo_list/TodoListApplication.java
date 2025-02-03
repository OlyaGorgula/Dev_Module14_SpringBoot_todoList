package com.goit5.todo_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);

		Note newNote1 = new Note();
		newNote1.setId(1L);
		newNote1.setTitle("Title 1");
		newNote1.setContent("Content 1");

		Note newNote2 = new Note();
		newNote2.setId(2L);
		newNote2.setTitle("Title 2");
		newNote2.setContent("Content 2");

		NoteService noteService = new NoteService();
		noteService.add(newNote1);
		noteService.add(newNote2);
		System.out.println("list note added:"+noteService.listAll());
		newNote2.setContent("Content update 2");
		noteService.update(newNote2);
		System.out.println("list note update:"+noteService.listAll());
		noteService.deleteById(newNote1.getId());
		System.out.println("list note delete:"+noteService.listAll());
	}

}
