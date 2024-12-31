package com.harshitkumar.task_management_backend.dto;

import com.harshitkumar.task_management_backend.entity.Role;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreateRequest {

    private String name;

    private String username;

    private String email;

    private String password;

    private Role role = Role.USER;
}
