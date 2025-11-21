package com.example.chessapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TournamentFinishDto {
    @NotNull
    private UUID winnerId;
}