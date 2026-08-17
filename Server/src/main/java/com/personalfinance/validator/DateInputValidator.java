package com.personalfinance.validator;

import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class DateInputValidator {

    public boolean isMonthValid(Integer month) {
        if (month < 1 || month > 12) {
            throw new AppException(ErrorCode.INVALID_MONTH);
        }

        return true;
    }

    public boolean isYearValid(Integer year) {
        if (year < 1970 || year > LocalDate.now().getYear()) {
            throw new AppException(ErrorCode.INVALID_YEAR);
        }

        return true;
    }

    public boolean isMonthAndYearValid(Integer month, Integer year) {
        isMonthValid(month);
        isYearValid(year);

        return true;
    }
}
