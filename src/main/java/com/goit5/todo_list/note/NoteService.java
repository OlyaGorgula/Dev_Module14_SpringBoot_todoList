package com.goit5.todo_list.note;

import com.goit5.todo_list.auth.EnumRole;
import com.goit5.todo_list.auth.entity.Role;
import com.goit5.todo_list.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final RoleRepository roleRepository;

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public List<Note> listAllByRoles(Collection<? extends GrantedAuthority> currentRoles) {
        List<String> listNameRole = currentRoles.stream().map(it -> it.getAuthority()).collect(Collectors.toList());
        return noteRepository.findAllByRoles(listNameRole);
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
            Role newRole = roleRepository.getById(note.getRole().getId());
            findNote.setRole(newRole);
        }
        noteRepository.save(findNote);
    }

    public Note getById(Long id) {
        return (id == null) ? null : noteRepository.findById(id).orElse(null);
    }

    public Role getRole(Collection<? extends GrantedAuthority> currentRoles, Long idRole) {
        if (idRole == null){
            Optional<String> findRole = currentRoles.stream()
                    .filter(it -> !roleRepository.findByName(it.getAuthority()).isReservedBySystem())
                    .findFirst()
                    .map(it -> it.getAuthority());
            if (findRole.isPresent()) {
                return roleRepository.findByName(findRole.get());
            } else {
                Optional<String> firstRole = currentRoles.stream().findFirst().map(it -> it.getAuthority());
                if (firstRole.isPresent()) {
                    return roleRepository.findByName(firstRole.get());
                }
                return roleRepository.findByName(EnumRole.USER.name());
            }
        } else {
            return roleRepository.getById(idRole);
        }
    }
}
