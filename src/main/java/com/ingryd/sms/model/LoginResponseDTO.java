package com.ingryd.sms.model;

import com.ingryd.sms.entity.User;

import lombok.*;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private User user;
    private String jwt;
}
