package com.intuit.assignment.chatserver.auth.service;
import com.intuit.assignment.chatserver.auth.repository.UserInfoRepository;
import com.intuit.assignment.chatserver.auth.model.UserInfoEntity;
import com.intuit.assignment.chatserver.auth.model.UserInfo;
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
    private UserInfoRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfoEntity> userInfoEntity = repository.findByName(username);

        return userInfoEntity.map(UserInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserInfoEntity userInfoEntity) {
        userInfoEntity.setPassword(encoder.encode(userInfoEntity.getPassword()));
        repository.save(userInfoEntity);
        return "User Added Successfully";
    }


}
