package com.goit5.todo_list.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @GetMapping("/test")
    public ModelAndView getTest(){
        ModelAndView result = new ModelAndView("test");

        return result;
    }
}
