package com.goit5.todo_list;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/note")
@Controller
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getListNote(){
        ModelAndView result = new ModelAndView("list");

        result.addObject("notes", noteService.listAll());
        return result;
    }

    @GetMapping("/edit")
    public ModelAndView getEditNote(@RequestParam(name = "id", defaultValue = "-1") long id){
        ModelAndView result = new ModelAndView("edit");

        result.addObject("note", noteService.readById(id));

        return result;
    }

    @PostMapping("/edit")
    public void postEditNote(
            @RequestParam(name = "id", defaultValue = "-1") long id,
            @RequestParam(name = "title", defaultValue = "Title") String title,
            @RequestParam(name = "content", defaultValue = "Content") String content,
            HttpServletResponse resp) throws IOException {

        Note newNote = new Note();
        newNote.setId(id);
        newNote.setTitle(title);
        newNote.setContent(content);
        noteService.save(newNote);

        resp.sendRedirect("/note/list");
    }

    @PostMapping("/delete")
    public void postDeleteNote(
            @RequestParam(name = "id") long id,
            HttpServletResponse resp) throws IOException {

        noteService.deleteById(id);

        resp.sendRedirect("/note/list");
    }

}
