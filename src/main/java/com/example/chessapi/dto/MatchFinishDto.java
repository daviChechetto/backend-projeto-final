package com.example.chessapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MatchFinishDto {
    @NotNull
    private String result; // WHITE_WIN, BLACK_WIN, DRAW
}