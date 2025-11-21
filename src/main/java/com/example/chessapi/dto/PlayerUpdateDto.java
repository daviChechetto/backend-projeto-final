package com.example.chessapi.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlayerUpdateDto {
    @Size(min = 3, max = 32)
    private String username;
    private String email;
    private Boolean active;
}