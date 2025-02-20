package com.goit5.todo_list.auth.service;

import com.goit5.todo_list.auth.AuthParams;
import com.goit5.todo_list.auth.EnumRole;
import com.goit5.todo_list.auth.UserByDefault;
import com.goit5.todo_list.auth.entity.Role;
import com.goit5.todo_list.auth.entity.Users;
import com.goit5.todo_list.auth.repository.RoleRepository;
import com.goit5.todo_list.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository usersRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserByDefault.isFirstRun(usersRepository, roleRepository);

        return usersRepository.findByEmail(username);
    }

    public AuthParams registration(String email, String password) {

        AuthParams authParams = new AuthParams();

        Users user = usersRepository.findByEmail(email);
        if (user != null) {
            authParams.setThereIsAlreadySuchAnEmail(true);
        }else {
            authParams.setThereIsAlreadySuchAnEmail(false);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setPassword("{bcrypt}" + passwordEncoder.encode(password));
            newUser.setEnabled(true);
            Role roleUser = new Role();
            roleUser.setName(EnumRole.USER.name());
            roleUser.setReservedBySystem(false);
            newUser.setRoles(Set.of(roleUser));
            for (Role role : newUser.getRoles()) {
                roleRepository.save(role);
            }
            usersRepository.save(newUser);
        }
        return authParams;
    }

}
