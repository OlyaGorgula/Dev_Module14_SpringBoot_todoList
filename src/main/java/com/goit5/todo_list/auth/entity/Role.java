package com.goit5.todo_list.auth.entity;

import com.goit5.todo_list.auth.entity.converter.FieldReservedBySystemConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Table(name = "role")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "reserved_by_system")
    @Convert(converter = FieldReservedBySystemConverter.class)
    private boolean reservedBySystem;

    @Override
    public String toString() {
        return "Role("+ id + name + " ," + reservedBySystem+")";
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }
}
