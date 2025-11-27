package com.example.chessapi.controller;

import com.example.chessapi.dto.PasswordUpdateDto;
import com.example.chessapi.dto.PlayerCreateDto;
import com.example.chessapi.dto.PlayerRankingDto;
import com.example.chessapi.dto.PlayerUpdateDto;
import com.example.chessapi.model.Match;
import com.example.chessapi.model.Player;
import com.example.chessapi.service.MatchService;
import com.example.chessapi.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public List<Player> list() {
        return playerService.list();
    }

    @GetMapping("/{id}")
    public Player get(@PathVariable UUID id) {
        return playerService.get(id);
    }

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

    // ✅ SOMENTE leaderboard paginado (não existe mais duplicação)
    @GetMapping("/leaderboard")
    public Page<Player> leaderboard(
            @PageableDefault(size = 20, sort = "rating", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return playerService.leaderboard(pageable);
    }

    @GetMapping("/{id}/rank")
    public PlayerRankingDto rank(@PathVariable UUID id) {
        return playerService.getPlayerRank(id);
    }
}
