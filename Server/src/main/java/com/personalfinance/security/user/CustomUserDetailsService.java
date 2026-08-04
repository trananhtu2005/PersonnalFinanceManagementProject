package com.personalfinance.security.user;

import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.repository.UserRepository;
import com.personalfinance.api.user.repository.UsernameRepository;
import com.personalfinance.validator.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UsernameRepository usernameRepository;
    private final EmailValidator emailValidator;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user;

        if (emailValidator.isEmail(login)) {
            user = userRepository.findByEmail(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } else {
            user = usernameRepository.findByUsername(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                    .getUser();
        }

        return new CustomUserDetails(user);
    }
}
