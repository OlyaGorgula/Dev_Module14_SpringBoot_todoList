package com.goit5.todo_list.auth.config;

import com.goit5.todo_list.auth.EnumRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login", "/registration", "/index_note").permitAll()
                        .requestMatchers("/user/**").hasAnyAuthority(EnumRole.ADMIN.name())
                        .requestMatchers("/role/**").hasAnyAuthority(EnumRole.ADMIN.name())
                        .requestMatchers("/note/**").hasAnyAuthority(
                                EnumRole.ADMIN.name(),
                                EnumRole.USER.name())
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/note/list")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }

}
