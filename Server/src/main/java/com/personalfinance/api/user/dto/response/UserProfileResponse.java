package com.personalfinance.api.user.dto.response;

import com.personalfinance.api.user.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private final Integer id;
    private final String email;
    private final String username;
    private final Role role;
}
