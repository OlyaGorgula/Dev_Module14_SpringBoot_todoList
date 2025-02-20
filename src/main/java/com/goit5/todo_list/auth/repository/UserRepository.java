package com.goit5.todo_list.auth.repository;


import com.goit5.todo_list.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("select u from Users u where u.email = :email")
    Users findByEmail(@Param("email") String email);

    @Query("select count(*) from Users")
    int countUser();
}
