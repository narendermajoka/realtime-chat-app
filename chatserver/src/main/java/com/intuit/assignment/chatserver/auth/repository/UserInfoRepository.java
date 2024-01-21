package com.intuit.assignment.chatserver.auth.repository;

import com.intuit.assignment.chatserver.auth.model.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
    Optional<UserInfoEntity> findByName(String username);
}
