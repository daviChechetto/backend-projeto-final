package com.example.chessapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PlayerRankingDto {
    private UUID playerId;
    private String username;
    private Integer rating;
    private Long rank;
}
