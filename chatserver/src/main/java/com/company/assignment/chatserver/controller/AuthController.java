package com.company.assignment.chatserver.controller;

import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.service.impl.JwtService;
import com.company.assignment.chatserver.service.impl.UserInfoService;
import com.company.assignment.chatserver.model.AuthRequest;
import com.company.assignment.chatserver.entity.UserEntity;
import com.company.assignment.chatserver.model.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public ResponseWrapper<String> addNewUser(@RequestBody UserEntity userEntity) {
        service.addUser(userEntity);
        return new ResponseWrapper<>(MessageConstants.USER_CREATED);
    }
    @PostMapping("/generate/token")
    public ResponseWrapper<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return new ResponseWrapper<>(jwtService.generateToken(authRequest.getUsername()),null);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}