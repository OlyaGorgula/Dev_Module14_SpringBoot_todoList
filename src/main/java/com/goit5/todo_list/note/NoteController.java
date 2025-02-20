package com.goit5.todo_list.note;

import com.goit5.todo_list.auth.EnumRole;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/note")
@Controller
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getListNote(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView result = new ModelAndView("list");

        Optional<? extends GrantedAuthority> isAdmin = userDetails.getAuthorities().stream()
                .filter(it -> it.getAuthority().equals(EnumRole.ADMIN.name())).findFirst();

        result.addObject("isAdmin", isAdmin.isPresent());
        result.addObject("notes", noteService.listAllByRoles(userDetails.getAuthorities()));

        return result;
    }

    @GetMapping("/edit")
    public ModelAndView getEditNote(@RequestParam(name = "id", required = false) Long id) {
        ModelAndView result = new ModelAndView("edit");

        Note note = noteService.readById(id);
        result.addObject("note", noteService.readById(id));
        result.addObject("name_role", (id == null) ? "" : note.getRole().getName());

        return result;
    }

    @PostMapping("/edit")
    public void postEditNote(@RequestParam(name = "id", required = false) Long id,
                             @RequestParam(name = "title", defaultValue = "Title") String title,
                             @RequestParam(name = "content", defaultValue = "Content") String content,
                             @RequestParam(name = "id_role", required = false) Long idRole,
                             @AuthenticationPrincipal UserDetails userDetails,
                             HttpServletResponse resp
            ) throws IOException {

        Note newNote = new Note();
        newNote.setId(id);
        newNote.setTitle(title);
        newNote.setContent(content);
        newNote.setRole(noteService.getRole(userDetails.getAuthorities(), idRole));
        noteService.save(newNote);

        resp.sendRedirect("/note/list");
    }

    @PostMapping("/delete")
    public void postDeleteNote(@RequestParam(name = "id") Long id,
                               HttpServletResponse resp) throws IOException {
        noteService.deleteById(id);

        resp.sendRedirect("/note/list");
    }

}
