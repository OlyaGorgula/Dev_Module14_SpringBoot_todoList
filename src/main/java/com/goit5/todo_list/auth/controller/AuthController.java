package com.goit5.todo_list.auth.controller;

import com.goit5.todo_list.auth.service.AuthService;
import com.goit5.todo_list.note.NoteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class AuthController {
    private final AuthService authService;
    private final NoteService noteService;

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public ModelAndView registrationPage(@RequestParam(name = "isDuplicateEmail",
            defaultValue = "false", required = false) boolean isDuplicateEmail) {
        ModelAndView result = new ModelAndView("auth/registration");

        result.addObject("isDuplicateEmail", isDuplicateEmail);
        return result;
    }

    @PostMapping("/registration")
    public void registration(@RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password,
                             HttpServletResponse resp) throws IOException {

        if (authService.registration(username, password)) {
            resp.sendRedirect("/registration?isDuplicateEmail=true");
        } else {
            resp.sendRedirect("/login");
        }
    }

    @GetMapping("/index")
    public ModelAndView indexPage() {
        ModelAndView result = new ModelAndView("index");

        result.addObject("notes", noteService.listAll());
        return result;
    }
}
