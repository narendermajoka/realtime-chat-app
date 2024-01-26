package com.company.assignment.chatserver.service.impl;
import com.company.assignment.chatserver.model.UserInfo;
import com.company.assignment.chatserver.repository.UserEntityRepository;
import com.company.assignment.chatserver.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userInfoEntity = userEntityRepository.findByEmail(username);

        return userInfoEntity.map(UserInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserEntity userEntity) {
        userEntity.setEmail(userEntity.getEmail().toLowerCase());
        if(userEntityRepository.existsByEmail(userEntity.getEmail())){
            throw new IllegalArgumentException("Username already exists!");
        }
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntityRepository.save(userEntity);
        return "User Added Successfully";
    }


}
