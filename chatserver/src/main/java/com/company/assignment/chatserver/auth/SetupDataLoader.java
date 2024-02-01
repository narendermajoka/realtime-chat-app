package com.company.assignment.chatserver.auth;

import com.company.assignment.chatserver.auth.entity.PrivilegeEntity;
import com.company.assignment.chatserver.auth.entity.RoleEntity;
import com.company.assignment.chatserver.auth.entity.UserEntity;
import com.company.assignment.chatserver.auth.repository.PrivilegeRepository;
import com.company.assignment.chatserver.auth.repository.RoleRepository;
import com.company.assignment.chatserver.auth.repository.UserEntityRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class SetupDataLoader {
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setUpRolesAndPrivileges() {
        PrivilegeEntity readAllRoomsPrivilege
                = createPrivilegeIfNotFound(AuthConstants.READ_ALL_ROOMS);
        PrivilegeEntity readRoomMessages
                = createPrivilegeIfNotFound(AuthConstants.READ_ROOM_MESSAGES);

        PrivilegeEntity createRoomPrivilege
                = createPrivilegeIfNotFound(AuthConstants.CREATE_ROOM);
        PrivilegeEntity deleteRoomPrivilege
                = createPrivilegeIfNotFound(AuthConstants.DELETE_ROOM);
        PrivilegeEntity addUserInRoomPrivilege
                = createPrivilegeIfNotFound(AuthConstants.ADD_USER_IN_ROOM);
        PrivilegeEntity writeMessageInRoomPrivilege
                = createPrivilegeIfNotFound(AuthConstants.WRITE_MESSAGE_IN_ROOM);


        List<PrivilegeEntity> userPrivileges = Arrays.asList(readAllRoomsPrivilege, readRoomMessages, createRoomPrivilege, addUserInRoomPrivilege, writeMessageInRoomPrivilege);

        List<PrivilegeEntity> adminPrivileges = new ArrayList<>();
        adminPrivileges.addAll(userPrivileges);
        adminPrivileges.add(deleteRoomPrivilege);

        createRoleIfNotFound(AuthConstants.ROLE_ADMIN, adminPrivileges);
        createRoleIfNotFound(AuthConstants.ROLE_USER, userPrivileges);

        boolean systemUserExists = userRepository.existsByEmail("system@company.com");
        if (!systemUserExists) {
            RoleEntity adminRole = roleRepository.findByName(AuthConstants.ROLE_ADMIN);
            UserEntity user = new UserEntity();
            user.setFirstName("System");
            user.setLastName("User");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEmail("system@company.com");
            user.setRoles(Collections.singletonList(adminRole));
            userRepository.save(user);
        }
    }

    @Transactional
    PrivilegeEntity createPrivilegeIfNotFound(String name) {

        PrivilegeEntity privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new PrivilegeEntity();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    RoleEntity createRoleIfNotFound(
            String name, List<PrivilegeEntity> privileges) {

        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
