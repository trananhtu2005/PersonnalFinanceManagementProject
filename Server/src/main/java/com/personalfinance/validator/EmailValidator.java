package com.personalfinance.validator;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public boolean isEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
