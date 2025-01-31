package com.goit5.todo_list;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    List<Note> listAll(){

        return new ArrayList<>();
    }

    Note add(Note note){

        return new Note();
    }

    void deleteById(long id){

    }

    void update(Note note){

    }

    Note getById(long id){

        return new Note();
    }

}
