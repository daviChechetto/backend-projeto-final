package com.chessapi.controller;

import com.chessapi.dto.MatchCreateDTO;
import com.chessapi.dto.MatchUpdateDTO;
import com.chessapi.model.Match;
import com.chessapi.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService svc;
    public MatchController(MatchService svc) { this.svc = svc; }

    @GetMapping
    public List<Match> list() { return svc.listAll(); }

    @GetMapping("/{id}")
    public Match get(@PathVariable UUID id) { return svc.getById(id); }

    @PostMapping
    public Match create(@RequestBody MatchCreateDTO dto) {
        return svc.create(dto.playerWhiteId, dto.playerBlackId, dto.moves, dto.tournamentId);
    }

    @PutMapping("/{id}")
    public Match updateResult(@PathVariable UUID id, @RequestBody MatchUpdateDTO dto) {
        return svc.updateResult(id, dto.result, dto.moves);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { svc.delete(id); }
}
