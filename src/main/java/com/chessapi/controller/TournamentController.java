package com.chessapi.controller;

import com.chessapi.dto.TournamentDTO;
import com.chessapi.model.Tournament;
import com.chessapi.service.TournamentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService svc;
    public TournamentController(TournamentService svc) { this.svc = svc; }

    @GetMapping
    public List<Tournament> list() { return svc.listAll(); }

    @GetMapping("/{id}")
    public Tournament get(@PathVariable UUID id) { return svc.getById(id); }

    @PostMapping
    public Tournament create(@RequestBody TournamentDTO dto) {
        return svc.create(dto.id, dto.name, dto.location, dto.startDate, dto.endDate);
    }

    @PutMapping("/{id}")
    public Tournament update(@PathVariable UUID id, @RequestBody TournamentDTO dto) {
        return svc.update(id, dto.name, dto.location, dto.startDate, dto.endDate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { svc.delete(id); }
}
