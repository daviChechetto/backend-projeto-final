package com.example.chessapi.controller;

import com.example.chessapi.dto.TournamentCreateDto;
import com.example.chessapi.dto.TournamentFinishDto;
import com.example.chessapi.model.Match;
import com.example.chessapi.model.Tournament;
import com.example.chessapi.service.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Tournament> create(@Valid @RequestBody TournamentCreateDto dto) {
        return ResponseEntity.ok(tournamentService.create(dto));
    }

    @GetMapping
    public List<Tournament> list() { return tournamentService.list(); }

    @GetMapping("/{id}")
    public Tournament get(@PathVariable UUID id) { return tournamentService.get(id); }

    @PatchMapping("/{id}/join")
    public Tournament join(@PathVariable UUID id, @RequestParam UUID playerId) {
        return tournamentService.join(id, playerId);
    }

    @PatchMapping("/{id}/start")
    public Tournament start(@PathVariable UUID id) { return tournamentService.start(id); }

    @PatchMapping("/{id}/finish")
    public Tournament finish(@PathVariable UUID id, @RequestBody TournamentFinishDto dto) {
        return tournamentService.finish(id, dto);
    }

    @GetMapping("/{id}/matches")
    public List<Match> matches(@PathVariable UUID id) { return tournamentService.matches(id); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tournamentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}