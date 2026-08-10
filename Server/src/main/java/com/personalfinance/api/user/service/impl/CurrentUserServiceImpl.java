package com.personalfinance.api.user.service.impl;

import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.service.CurrentUserService;
import com.personalfinance.security.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        return user;
    }
}
