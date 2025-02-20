package com.goit5.todo_list.auth.service;


import com.goit5.todo_list.auth.entity.Role;
import com.goit5.todo_list.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    public Role readById(Long id) {
        Role findRole = getById(id);
        return (findRole == null) ? new Role() : findRole;
    }

    public void save(Role role) {
        Role findRole = getById(role.getId());
        if (findRole == null) {
            findRole = role;
            findRole.setName(generatorNameRole(role.getName()));
        } else {
            findRole.setReservedBySystem(role.isReservedBySystem());
            if (!role.getName().equals(findRole.getName())) {
                findRole.setName(generatorNameRole(role.getName()));
            }
        }
        roleRepository.save(findRole);
    }

    private String generatorNameRole(String oldName) {
        String newNameRole = oldName;
        int version = 1;
        while (roleRepository.findByName(newNameRole) != null) {
            newNameRole = newNameRole + version;
            version ++;
        }
        return newNameRole;
    }

    public void deleteById(Long id) {
        if (getById(id) != null) {
            roleRepository.deleteById(id);
        }
    }

    public Role getById(Long id) {
        return (id == null) ? null : roleRepository.findById(id).orElse(null);
    }
}
