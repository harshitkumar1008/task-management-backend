package com.harshitkumar.task_management_backend.dto;

import com.harshitkumar.task_management_backend.entity.Priority;
import com.harshitkumar.task_management_backend.entity.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RefreshTokenRequest {

    @NotBlank(message = "Access Token is required")
    private String accessToken;

    @NotBlank(message = "Refresh Token is required")
    private String refreshToken;
}
