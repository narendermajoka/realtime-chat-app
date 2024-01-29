package com.company.assignment.chatserver.auth.controller;

import com.company.assignment.chatserver.auth.AuthConstants;
import com.company.assignment.chatserver.auth.entity.RoleEntity;
import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.model.UserInfo;
import com.company.assignment.chatserver.auth.service.JwtService;
import com.company.assignment.chatserver.auth.service.UserInfoService;
import com.company.assignment.chatserver.model.AuthRequest;
import com.company.assignment.chatserver.auth.entity.UserEntity;
import com.company.assignment.chatserver.model.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserInfoService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/signup")
    public ResponseWrapper<String> addNewUser(@RequestBody UserEntity userEntity) {
        service.addUser(userEntity, AuthConstants.ROLE_USER);
        return new ResponseWrapper<>(MessageConstants.USER_CREATED);
    }
    @PostMapping("/generate/token")
    public ResponseWrapper<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            UserInfo userInfo = (UserInfo) authentication.getPrincipal();
            return new ResponseWrapper<>(jwtService.generateToken(userInfo),null);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}