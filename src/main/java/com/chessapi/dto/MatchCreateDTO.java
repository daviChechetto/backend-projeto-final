package com.chessapi.dto;

import java.util.UUID;

public class MatchCreateDTO {
    public UUID playerWhiteId;
    public UUID playerBlackId;
    public String moves;
    public UUID tournamentId; // optional
}
