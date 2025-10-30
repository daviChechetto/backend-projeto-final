package com.chessapi.dto;

import java.util.UUID;
import java.util.List;

public class MatchDTO {
    public UUID playerWhiteId;
    public UUID playerBlackId;
    public UUID tournamentId;
    public String moves;
    public String result;
}
