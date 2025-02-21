package com.goit5.todo_list.auth;

import com.goit5.todo_list.auth.entity.Role;
import com.goit5.todo_list.auth.entity.Users;
import com.goit5.todo_list.auth.repository.RoleRepository;
import com.goit5.todo_list.auth.repository.UserRepository;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
public class UserByDefault {

    public static void isFirstRun(UserRepository usersRepository, RoleRepository roleRepository) {
        if (usersRepository.countUser() == 0) {
            saveUserDefault(EnumRole.ADMIN, usersRepository, roleRepository);
            saveUserDefault(EnumRole.USER, usersRepository, roleRepository);
            saveUserDefault(EnumRole.GUEST, usersRepository, roleRepository);
        }
    }

    private static void saveUserDefault(EnumRole nameRole,
                                        UserRepository usersRepository,
                                        RoleRepository roleRepository) {
        Users user = usersRepository.findByEmail(nameRole.name());
        if (user == null) {
            user = switch (nameRole) {
                case ADMIN -> getAdminRole(roleRepository);
                case USER -> getUserRole(roleRepository);
                case GUEST -> getGuestRole(roleRepository);
            };
            saveRole(user, roleRepository);
            usersRepository.save(user);
        }
    }

    private static void saveRole(Users user, RoleRepository roleRepository) {
        for (Role role : user.getRoles()) {
            roleRepository.save(role);
        }
    }

    private static Users getAdminRole(RoleRepository roleRepository) {
        return setRoleDefault(
                "admin@i.ua",
                "{bcrypt}$2a$12$/UmCcwf07UC8aTTW.rv3Z.qWBmKXv8HO6lNNpfSb5bOXVQhqIYm8e",
                EnumRole.ADMIN.name(),
                roleRepository);
    }

    private static Users getUserRole(RoleRepository roleRepository) {
        return setRoleDefault(
                "user@i.ua",
                "{noop}123",
                EnumRole.USER.name(),
                roleRepository);
    }

    private static Users getGuestRole(RoleRepository roleRepository) {
        return setRoleDefault(
                "guest@i.ua",
                "{noop}123",
                EnumRole.GUEST.name(),
                roleRepository);
    }

    private static Users setRoleDefault(String email, String password, String nameRole, RoleRepository roleRepository) {
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);
        Role roleUser = roleRepository.findByName(nameRole);
        if (roleUser == null) {
            roleUser = new Role();
            roleUser.setName(nameRole);
            roleUser.setReservedBySystem(true);
        }
        user.setRoles(Set.of(roleUser));
        return user;
    }
}
