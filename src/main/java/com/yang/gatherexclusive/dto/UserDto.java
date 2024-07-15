package com.yang.gatherexclusive.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @NotEmpty(message = "Email should not be empty")
    private String email;
}
