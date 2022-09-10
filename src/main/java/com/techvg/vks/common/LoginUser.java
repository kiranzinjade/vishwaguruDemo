package com.techvg.vks.common;

import com.techvg.vks.security.exception.UserNotFoundException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUser {

    public static String getUser(){
        String currentUserName =null;
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
             currentUserName = authentication.getName();
        }
        else
            throw new UserNotFoundException("No logged in user found " );
        return currentUserName;
    }
}
