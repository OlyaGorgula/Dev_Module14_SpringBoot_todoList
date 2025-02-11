package com.goit5.todo_list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public void deleteById(Long id) {
        if (getById(id) != null) {
            noteRepository.deleteById(id);
        }
    }

    public Note readById(Long id) {
        Note findNote = getById(id);
        return (findNote == null) ? new Note() : findNote;
    }

    public void save(Note note) {
        Note findNote = getById(note.getId());
        if (findNote == null) {
            findNote = note;
        } else {
            findNote.setTitle(note.getTitle());
            findNote.setContent(note.getContent());
        }
        noteRepository.save(findNote);
    }

    public Note getById(Long id) {
        return (id == null) ? null : noteRepository.findById(id).orElse(null);
    }

}
