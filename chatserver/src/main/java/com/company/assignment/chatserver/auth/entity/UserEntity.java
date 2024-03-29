package com.company.assignment.chatserver.auth.entity;

import com.company.assignment.chatserver.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE user_id=?")
@Where(clause = "deleted=false")
public class UserEntity  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    @Column(name = "email" , nullable = false, unique = true, length = 50)
    private String email;
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_role_mapping",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private List<RoleEntity> roles;

    @Column(name = "deleted")
    private boolean deleted;

    public String getFullName(){
        return this.firstName + " "+ this.lastName;
    }

}