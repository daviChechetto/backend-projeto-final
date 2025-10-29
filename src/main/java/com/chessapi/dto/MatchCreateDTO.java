package com.chessapi.dto;

public class MatchCreateDTO {
    public Long playerWhiteId;
    public Long playerBlackId;
    public String moves;
    public Long tournamentId; // optional
}
