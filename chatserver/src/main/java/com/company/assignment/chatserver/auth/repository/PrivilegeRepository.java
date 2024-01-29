package com.company.assignment.chatserver.auth.repository;

import com.company.assignment.chatserver.auth.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByName(String name);
}
