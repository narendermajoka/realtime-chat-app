package com.company.assignment.chatserver.repository;

import com.company.assignment.chatserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
