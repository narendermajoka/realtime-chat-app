package com.company.assignment.chatserver.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "role")
public class RoleEntity {

    public RoleEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "name")
    private String name;
//    @ManyToMany(mappedBy = "roles")
//    private Collection<UserEntity> users;

    @ManyToMany
    @JoinTable(
            name = "role_privilege_mapping",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "privilege_id"))
    private List<PrivilegeEntity> privileges;
}
