package com.goit5.todo_list.auth.controller;

import com.goit5.todo_list.auth.entity.Role;
import com.goit5.todo_list.auth.service.RoleService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/role")
@Controller
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/list")
    public ModelAndView getListRole() {
        ModelAndView result = new ModelAndView("auth/user/list_role");

        result.addObject("roles", roleService.listAll());
        return result;
    }

    @GetMapping("/edit")
    public ModelAndView getEditRole(@RequestParam(name = "id", required = false) Long id) {
        ModelAndView result = new ModelAndView("auth/user/edit_role");

        result.addObject("role", roleService.readById(id));

        return result;
    }

    @PostMapping("/edit")
    public void postEditRole(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "reservedBySystem", defaultValue = "false") boolean reservedBySystem,
            HttpServletResponse resp) throws IOException {

        Role newRole = new Role();
        newRole.setId(id);
        newRole.setName(name);
        newRole.setReservedBySystem(reservedBySystem);
        roleService.save(newRole);

        resp.sendRedirect("/role/list");
    }

    @PostMapping("/delete")
    public void postDeleteRole(
            @RequestParam(name = "id") Long id,
            HttpServletResponse resp) throws IOException {

        roleService.deleteById(id);

        resp.sendRedirect("/role/list");
    }
}
