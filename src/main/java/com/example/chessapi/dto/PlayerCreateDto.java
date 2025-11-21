package com.example.chessapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlayerCreateDto {
    @NotBlank @Size(min = 3, max = 32)
    private String username;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;
}