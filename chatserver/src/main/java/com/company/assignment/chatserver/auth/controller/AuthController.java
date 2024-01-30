package com.company.assignment.chatserver.auth.controller;

import com.company.assignment.chatserver.auth.AuthConstants;
import com.company.assignment.chatserver.auth.entity.RoleEntity;
import com.company.assignment.chatserver.constants.MessageConstants;
import com.company.assignment.chatserver.model.User;
import com.company.assignment.chatserver.model.UserInfo;
import com.company.assignment.chatserver.auth.service.JwtService;
import com.company.assignment.chatserver.auth.service.UserInfoService;
import com.company.assignment.chatserver.model.AuthRequest;
import com.company.assignment.chatserver.auth.entity.UserEntity;
import com.company.assignment.chatserver.model.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {
    @Autowired
    private UserInfoService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/signup")
    @Operation(summary = "Create a new user")
    public ResponseWrapper<String> addNewUser(@Valid @RequestBody User user) {
        service.addUser(user, AuthConstants.ROLE_USER);
        return new ResponseWrapper<>(MessageConstants.USER_CREATED);
    }
    @PostMapping("/generate/token")
    @Operation(summary = "Generate token for a user")
    public ResponseWrapper<String> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            UserInfo userInfo = (UserInfo) authentication.getPrincipal();
            return new ResponseWrapper<>(jwtService.generateToken(userInfo),null);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}