package com.example.chessapi.repository;

import com.example.chessapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Optional<Player> findByUsernameIgnoreCase(String username);
    Optional<Player> findByEmailIgnoreCase(String email);
    List<Player> findTop50ByActiveTrueOrderByRatingDesc();
}