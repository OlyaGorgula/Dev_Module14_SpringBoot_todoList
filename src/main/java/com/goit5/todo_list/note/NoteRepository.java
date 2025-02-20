package com.goit5.todo_list.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query(nativeQuery = true, value =
            "SELECT note.* FROM note " +
                    "LEFT JOIN role ON role.id = note.id_role "+
                    "WHERE role.name IN (:listNameRoles)")
    List<Note> findAllByRoles(@Param("listNameRoles")List<String> listNameRoles);
}
