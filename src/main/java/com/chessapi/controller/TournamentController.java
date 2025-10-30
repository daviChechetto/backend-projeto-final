package com.chessapi.controller;

import com.chessapi.dto.TournamentDTO;
import com.chessapi.model.Tournament;
import com.chessapi.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService svc;

    public TournamentController(TournamentService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<TournamentDTO> list() {
        return svc.listAll().stream()
                .map(TournamentDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> get(@PathVariable UUID id) {
        Tournament t = svc.getById(id);
        return ResponseEntity.ok(new TournamentDTO(t));
    }

    @PostMapping
    public ResponseEntity<TournamentDTO> create(@RequestBody TournamentDTO dto) {
        Tournament saved = svc.create(dto);
        return ResponseEntity.ok(new TournamentDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentDTO> update(@PathVariable UUID id, @RequestBody TournamentDTO dto) {
        Tournament updated = svc.update(id, dto);
        return ResponseEntity.ok(new TournamentDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
