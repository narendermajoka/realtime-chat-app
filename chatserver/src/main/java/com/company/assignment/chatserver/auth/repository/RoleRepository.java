package com.company.assignment.chatserver.auth.repository;

import com.company.assignment.chatserver.auth.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
