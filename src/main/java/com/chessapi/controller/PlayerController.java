package com.chessapi.controller;

import com.chessapi.dto.PlayerDTO;
import com.chessapi.dto.PlayerRequestDTO;
import com.chessapi.dto.PlayerResponseDTO;
import com.chessapi.model.Player;
import com.chessapi.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService svc;

    public PlayerController(PlayerService svc) { this.svc = svc; }

    @GetMapping
    public List<PlayerResponseDTO> list(@RequestParam(required = false) String name) {
        return (name != null ? svc.findByName(name) : svc.listAll())
                .stream()
                .map(PlayerResponseDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> get(@PathVariable UUID id) {
        return svc.findById(id)
                .map(player -> ResponseEntity.ok(new PlayerResponseDTO(player)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlayerResponseDTO> create(@RequestBody PlayerRequestDTO dto) {
        Player created = svc.create(dto);
        return ResponseEntity.status(201).body(new PlayerResponseDTO(created));
    }

    @PutMapping("/{id}")
    public PlayerResponseDTO update(@PathVariable UUID id, @RequestBody PlayerRequestDTO dto) {
        Player updated = svc.update(id, dto);
        return new PlayerResponseDTO(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
