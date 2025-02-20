package com.goit5.todo_list.auth.controller;

import com.goit5.todo_list.auth.EnumRole;
import com.goit5.todo_list.auth.entity.Users;
import com.goit5.todo_list.auth.service.RoleService;
import com.goit5.todo_list.auth.service.UserService;
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
@RequestMapping("/user")
@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/list")
    public ModelAndView getListUser() {
        ModelAndView result = new ModelAndView("auth/user/list_user");

        result.addObject("users", userService.listAll());
        return result;
    }

    @GetMapping("/edit")
    public ModelAndView getEditUser(@RequestParam(name = "id") Long id) {
        ModelAndView result = new ModelAndView("auth/user/edit_user");

        Users user = userService.readById(id);
        result.addObject("user", user);
        result.addObject("roles", user.getRoles());
        result.addObject("rolesAll", roleService.listAll());
        result.addObject("name_role", EnumRole.USER.name());

        return result;
    }

    @PostMapping("/edit")
    public void postEditUser(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "enabled", required = false) boolean enabled,
            HttpServletResponse resp) throws IOException {

        Users newUser = new Users();
        newUser.setId(id);
        newUser.setEnabled(enabled);
        userService.save(newUser);

        resp.sendRedirect("/user/list");
    }

    @PostMapping("/delete")
    public void postDeleteUser(@RequestParam(name = "id") Long id,
                               HttpServletResponse resp) throws IOException {

        userService.deleteById(id);

        resp.sendRedirect("/user/list");
    }

    @PostMapping("/add_role")
    public void postAddUser(@RequestParam(name = "id_user") Long idUser,
                            @RequestParam(name = "id_role") Long idRole,
                            HttpServletResponse resp) throws IOException {

        userService.addRoleById(idUser, idRole);

        resp.sendRedirect("/user/edit?id="+idUser);
    }

    @PostMapping("/delete_role")
    public void postDeleteRole(@RequestParam(name = "id_user") Long idUser,
                               @RequestParam(name = "id_role") Long idRole,
                               HttpServletResponse resp) throws IOException {

        userService.deleteRoleById(idUser, idRole);

        resp.sendRedirect("/user/edit?id="+idUser);
    }
}
