package com.chessapi.repository;

import com.chessapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findByNameContainingIgnoreCase(String name);
    boolean existsByEmail(String email);
}
