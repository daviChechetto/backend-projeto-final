package com.example.chessapi.controller;

import com.example.chessapi.dto.PasswordUpdateDto;
import com.example.chessapi.dto.PlayerCreateDto;
import com.example.chessapi.dto.PlayerUpdateDto;
import com.example.chessapi.model.Match;
import com.example.chessapi.model.Player;
import com.example.chessapi.service.MatchService;
import com.example.chessapi.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<Player> create(@Valid @RequestBody PlayerCreateDto dto) {
        return ResponseEntity.ok(playerService.create(dto));
    }

    @GetMapping
    public List<Player> list() { return playerService.list(); }

    @GetMapping("/{id}")
    public Player get(@PathVariable UUID id) { return playerService.get(id); }

    @PutMapping("/{id}")
    public Player update(@PathVariable UUID id, @Valid @RequestBody PlayerUpdateDto dto) {
        return playerService.update(id, dto);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @Valid @RequestBody PasswordUpdateDto dto) {
        playerService.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        playerService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/matches")
    public List<Match> history(@PathVariable UUID id) {
        return matchService.historyForPlayer(id);
    }

    @GetMapping("/leaderboard")
    public List<Player> leaderboard() {
        return playerService.leaderboard();
    }
}