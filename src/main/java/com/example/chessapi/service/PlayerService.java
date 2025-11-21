package com.example.chessapi.service;

import com.example.chessapi.dto.PasswordUpdateDto;
import com.example.chessapi.dto.PlayerCreateDto;
import com.example.chessapi.dto.PlayerUpdateDto;
import com.example.chessapi.model.Player;
import com.example.chessapi.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Player create(PlayerCreateDto dto) {
        playerRepo.findByUsernameIgnoreCase(dto.getUsername()).ifPresent(p -> {
            throw new IllegalArgumentException("Username already exists");
        });
        playerRepo.findByEmailIgnoreCase(dto.getEmail()).ifPresent(p -> {
            throw new IllegalArgumentException("Email already exists");
        });

        Player p = Player.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .rating(1200).wins(0).losses(0).draws(0)
                .active(true)
                .build();
        return playerRepo.save(p);
    }

    public Player get(UUID id) {
        return playerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Player not found"));
    }

    public List<Player> list() { return playerRepo.findAll(); }

    @Transactional
    public Player update(UUID id, PlayerUpdateDto dto) {
        Player p = get(id);
        if (dto.getUsername() != null) p.setUsername(dto.getUsername());
        if (dto.getEmail() != null) p.setEmail(dto.getEmail());
        if (dto.getActive() != null) p.setActive(dto.getActive());
        return playerRepo.save(p);
    }

    @Transactional
    public void updatePassword(UUID id, PasswordUpdateDto dto) {
        Player p = get(id);
        p.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        playerRepo.save(p);
    }

    public List<Player> leaderboard() {
        return playerRepo.findTop50ByActiveTrueOrderByRatingDesc();
    }

    @Transactional
    public void deactivate(UUID id) {
        Player p = get(id);
        p.setActive(false);
        playerRepo.save(p);
    }
}