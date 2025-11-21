package com.example.chessapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TournamentCreateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String format; // SWISS, ROUND_ROBIN, KNOCKOUT
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @NotNull
    private UUID ownerId;
    private Integer maxPlayers = 32;
}