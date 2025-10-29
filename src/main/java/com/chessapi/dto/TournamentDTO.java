package com.chessapi.dto;

import java.time.LocalDate;
import java.util.UUID;

public class TournamentDTO {
    public UUID id;
    public String name;
    public String location;
    public LocalDate startDate;
    public LocalDate endDate;
}
