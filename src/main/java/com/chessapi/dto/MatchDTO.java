package com.chessapi.dto;

import java.util.List;
import java.util.UUID;

public class MatchDTO {
    public UUID playerWhiteId;
    public UUID playerBlackId;
    public UUID tournamentId;
    public String moves;
    public String result;
}
