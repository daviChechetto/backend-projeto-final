package com.example.chessapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MatchCreateDto {
    @NotNull
    private UUID whitePlayerId;
    @NotNull
    private UUID blackPlayerId;
    private UUID tournamentId;
    private String timeControl = "10+0";
}