package com.example.chessapi.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MatchHistoryDto {
    private UUID matchId;
    private UUID whitePlayerId;
    private UUID blackPlayerId;
    private String result;
    private String moves;
    private UUID tournamentId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String timeControl;
}
