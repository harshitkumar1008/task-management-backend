package com.harshitkumar.task_management_backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenResponse {

    private String accessToken;

    private String refreshToken;
}