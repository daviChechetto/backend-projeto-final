package com.chessapi.controller;

import com.chessapi.dto.PlayerDTO;
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
    public List<Player> list(@RequestParam(required = false) String name) {
        if (name != null) return svc.findByName(name);
        return svc.listAll();
    }

    @GetMapping("/{id}")
    // permite mandar o status code junto ao body
    public ResponseEntity<Player> get(@PathVariable UUID id) {
        return svc.findById(id)
                .map(ResponseEntity::ok) // se houver um jogador com esse ID, o status HTTP é configurado como 200
                .orElse(ResponseEntity.notFound().build()); // status HTTP 404 se não houver jogador com o ID passado
    }

    @PostMapping
    public Player create(@RequestBody PlayerDTO dto) {
        return svc.create(dto.id, dto.name, dto.email, dto.elo, dto.country);
    }

    @PutMapping("/{id}")
    public Player update(@PathVariable UUID id, @RequestBody PlayerDTO dto) {
        return svc.update(id, dto.id, dto.name, dto.email, dto.elo, dto.country);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        svc.delete(id);
        return ResponseEntity.noContent().build(); // retorna HTTP 204 se deletar
    }
}
