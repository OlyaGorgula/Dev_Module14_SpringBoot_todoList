package com.goit5.todo_list;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.max;

@Service
public class NoteService {
    private List<Note> noteList = new ArrayList<>();
    private long maxId = -1L;

    static Note nullNote = new Note();
    static {
        nullNote.setId(-1L);
    }


    public List<Note> listAll(){
        return noteList;
    }

    public long getMaxId(){
        return maxId;
    }

    public Note add(Note note){

        maxId = max(note.getId(), maxId);

        Note findNode = getById(note.getId());
        if (findNode.getId() != -1){
            return findNode;
        }
        noteList.add(note);
        return note;
    }

    public void deleteById(long id){
        Note findNode = getById(id);
        if (findNode.getId() != -1){
            noteList.remove(findNode);
        }
    }

    public void update(Note note){
        Note findNode = getById(note.getId());
        if (findNode.getId() != -1){
            findNode.setContent(note.getContent());
            findNode.setTitle(note.getTitle());
        }
    }

    public Note getById(long id){
        Optional<Note> optionalNote = noteList.stream()
                .filter(it -> it.getId() == id)
                .findFirst();
        if (optionalNote.isPresent()){
            return  optionalNote.get();
        }
        return nullNote;
    }

}
