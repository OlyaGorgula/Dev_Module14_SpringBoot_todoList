package com.goit5.todo_list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;

    static Note nullNote = new Note();
    static {
        nullNote.setId(-1L);
    }

    public List<Note> listAll(){
        return noteRepository.findAll();
    }

    public void deleteById(long id){
        noteRepository.deleteById(""+id);
    }

    public Note update(Note note){
        return noteRepository.save(note);
    }

    public Note getById(long id){
        Optional<Note> findNote = noteRepository.findById(""+id);
        if (findNote.isPresent()){
            return findNote.get();
        }
        return nullNote;
    }

}
