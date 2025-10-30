package com.chessapi.service;

import com.chessapi.dto.PlayerRequestDTO;
import com.chessapi.model.Player;
import com.chessapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository repo;

    public PlayerService(PlayerRepository repo) {
        this.repo = repo;
    }

    public List<Player> listAll() {
        return repo.findAll();
    }

    public List<Player> findByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public Optional<Player> findById(UUID id) {
        return repo.findById(id);
    }

    public Player create(PlayerRequestDTO dto) {
        if (repo.existsByEmail(dto.email)) {
            throw new RuntimeException("Email já cadastrado");
        }
        Player p = new Player(dto.name, dto.email, dto.password, dto.country);
        return repo.save(p);
    }

    public Player update(UUID id, PlayerRequestDTO dto) {
        Player p = repo.findById(id).orElseThrow(() -> new RuntimeException("Player não encontrado"));
        if (dto.name != null) p.setName(dto.name);
        if (dto.email != null) p.setEmail(dto.email);
        if (dto.country != null) p.setCountry(dto.country);
        return repo.save(p);
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new RuntimeException("Player não encontrado");
        repo.deleteById(id);
    }
}
