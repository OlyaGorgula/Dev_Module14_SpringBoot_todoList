package com.goit5.todo_list.auth.service;


import com.goit5.todo_list.auth.entity.Role;
import com.goit5.todo_list.auth.entity.Users;
import com.goit5.todo_list.auth.repository.RoleRepository;
import com.goit5.todo_list.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository usersRepository;
    private final RoleRepository roleRepository;

    public List<Users> listAll() {
        return usersRepository.findAll();
    }

    public Users readById(Long id) {
        Users findUser = getById(id);
        return (findUser == null) ? new Users() : findUser;
    }

    public void save(Users user) {
        Users findUser = getById(user.getId());
        if (findUser != null) {
            findUser.setEnabled(user.getEnabled());
            usersRepository.save(findUser);
        }
    }

    public void deleteById(Long id) {
        if (getById(id) != null) {
            usersRepository.deleteById(id);
        }
    }

    interface operationWithRoles {
        void operation(Set<Role> roles, Role role);
    }

    public void deleteRoleById(Long idUser, Long idRole) {
        saveRole(idUser, idRole, (roles, role) -> roles.remove(role));
    }

    public void addRoleById(Long idUser, Long idRole) {
        saveRole(idUser, idRole, (roles, role) -> roles.add(role));
    }

    public void saveRole(Long idUser, Long idRole, operationWithRoles operationWithRoles) {
        Users user = usersRepository.getById(idUser);
        Role role = roleRepository.getById(idRole);
        if (user != null && role != null) {
            Set<Role> roles = user.getRoles();
            operationWithRoles.operation(roles, role);
            user.setRoles(roles);
            usersRepository.save(user);
        }
    }

    public Users getById(Long id) {
        return (id == null) ? null : usersRepository.findById(id).orElse(null);
    }
}
