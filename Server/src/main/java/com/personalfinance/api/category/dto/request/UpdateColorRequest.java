package com.personalfinance.api.category.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateColorRequest {

    private String name;
    private String code;

    public boolean isEmpty() {
        return name == null
                && code == null;
    }
}
