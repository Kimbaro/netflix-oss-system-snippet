package com.kimbaro.eureka.user.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCredentials {
    String email;
    String password;
}
