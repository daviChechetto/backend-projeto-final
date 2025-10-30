package com.chessapi.dto;

import com.chessapi.model.Player;
import java.util.UUID;

public class PlayerResponseDTO {
    public UUID id;
    public String name;
    public String email;
    public int elo;
    public String country;

    public PlayerResponseDTO(Player p) {
        this.id = p.getId();
        this.name = p.getName();
        this.email = p.getEmail();
        this.elo = p.getElo();
        this.country = p.getCountry();
    }
}
