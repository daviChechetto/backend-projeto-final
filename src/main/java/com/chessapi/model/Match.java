package com.chessapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // referencia os jogadores pelo ID
    @ManyToOne
    @JoinColumn(name = "player_white_id", nullable = false)
    private Player playerWhite;

    @ManyToOne
    @JoinColumn(name = "player_black_id", nullable = false)
    private Player playerBlack;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner; // null em caso de empate ou se o jogo ainda está em andamento

    private String result; // "white", "black", "draw", "ongoing"

    @Lob // indica que o campo vai armazenar uma grande quantidade de dados
    private String moves; // texto notado em PGN, usado muito no xadrez

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public Match() {}

    public Match(Player white, Player black, String moves) {
        this.playerWhite = white;
        this.playerBlack = black;
        this.moves = moves;
        this.result = "ongoing";
    }

    // getters e setters
    public UUID getId() { return id; }
    public Player getPlayerWhite() { return playerWhite; }
    public void setPlayerWhite(Player playerWhite) { this.playerWhite = playerWhite; }
    public Player getPlayerBlack() { return playerBlack; }
    public void setPlayerBlack(Player playerBlack) { this.playerBlack = playerBlack; }
    public Player getWinner() { return winner; }
    public void setWinner(Player winner) { this.winner = winner; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getMoves() { return moves; }
    public void setMoves(String moves) { this.moves = moves; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Tournament getTournament() { return tournament; }
    public void setTournament(Tournament tournament) { this.tournament = tournament; }
}
