package com.kimbaro.eureka.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    String email;

    public User(String email) {
        this.email = email;
    }
}
