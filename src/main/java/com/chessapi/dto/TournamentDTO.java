package com.chessapi.dto;

import com.chessapi.model.Tournament;
import java.time.LocalDate;
import java.util.UUID;

public class TournamentDTO {
    public UUID id;
    public String name;
    public String location;
    public LocalDate startDate;
    public LocalDate endDate;

    public TournamentDTO() {}

    public TournamentDTO(Tournament t) {
        this.id = t.getId();
        this.name = t.getName();
        this.location = t.getLocation();
        this.startDate = t.getStartDate();
        this.endDate = t.getEndDate();
    }
}
