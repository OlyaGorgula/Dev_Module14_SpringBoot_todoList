package com.goit5.todo_list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> listAll(){
        return noteRepository.findAll();
    }

    public void deleteById(long id){
        if (getById(id).getId() != -1) {
            noteRepository.deleteById(String.valueOf(id));
        }
    }

    public Note save(Note note){
        Note findNote = getById(note.getId());
        if (findNote.getId() == -1) {
            findNote = note;
        }else {
            findNote.setTitle(note.getTitle());
            findNote.setContent(note.getContent());
        }
        return noteRepository.save(findNote);
    }

    public Note getById(long id){
        Optional<Note> findNote = noteRepository.findById(String.valueOf(id));
        if (findNote.isPresent()){
            return findNote.get();
        }
        Note emptyNode = new Note();
        emptyNode.setId(-1);
        return emptyNode;
    }

}
