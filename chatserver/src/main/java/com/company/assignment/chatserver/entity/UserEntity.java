package com.company.assignment.chatserver.entity;

import com.company.assignment.chatserver.config.encryption.AttributeEncryptor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class UserEntity  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email" , nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roles")
    private String roles;

    @Column(name = "deleted")
    private boolean deleted;

    public String getFullName(){
        return this.firstName + " "+ this.lastName;
    }

}