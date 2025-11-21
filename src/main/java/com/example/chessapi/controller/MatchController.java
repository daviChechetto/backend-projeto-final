package com.example.chessapi.controller;

import com.example.chessapi.dto.MatchCreateDto;
import com.example.chessapi.dto.MatchFinishDto;
import com.example.chessapi.dto.MatchPgnDto;
import com.example.chessapi.model.Match;
import com.example.chessapi.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<Match> create(@Valid @RequestBody MatchCreateDto dto) {
        return ResponseEntity.ok(matchService.create(dto));
    }

    @GetMapping
    public List<Match> list() { return matchService.list(); }

    @GetMapping("/{id}")
    public Match get(@PathVariable UUID id) { return matchService.get(id); }


    @PatchMapping("/{id}/finish")
    public Match finish(@PathVariable UUID id, @Valid @RequestBody MatchFinishDto dto) {
        return matchService.finish(id, dto);
    }

    @PatchMapping("/{id}/pgn")
    public Match updatePgn(@PathVariable UUID id, @RequestBody MatchPgnDto dto) {
        return matchService.updatePgn(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        matchService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}