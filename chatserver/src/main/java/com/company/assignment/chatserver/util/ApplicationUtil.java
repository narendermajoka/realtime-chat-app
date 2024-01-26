package com.company.assignment.chatserver.util;

import com.company.assignment.chatserver.model.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationUtil {
    private ApplicationUtil(){}

    public static UserInfo getCurrentUser(){
        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        return (UserInfo)authenticationToken.getPrincipal();
    }
}
