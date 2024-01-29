package com.company.assignment.chatserver.auth.service;

import com.company.assignment.chatserver.auth.entity.UserEntity;
import com.company.assignment.chatserver.auth.repository.RoleRepository;
import com.company.assignment.chatserver.auth.repository.UserEntityRepository;
import com.company.assignment.chatserver.model.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userInfoEntity = userEntityRepository.findByEmail(username);

        return userInfoEntity.map(UserInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    @Transactional
    public void addUser(UserEntity userEntity, String userRole) {
        userEntity.setEmail(userEntity.getEmail().toLowerCase());
        if(userEntityRepository.existsByEmail(userEntity.getEmail())){
            throw new IllegalArgumentException("Username already exists!");
        }
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Collections.singletonList(roleRepository.findByName(userRole)));

        userEntityRepository.save(userEntity);
    }


}
