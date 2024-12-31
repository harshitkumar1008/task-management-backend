package com.harshitkumar.task_management_backend.dto;

import com.harshitkumar.task_management_backend.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateUserRequest {
    private Role role;
    private boolean isActive;
}
