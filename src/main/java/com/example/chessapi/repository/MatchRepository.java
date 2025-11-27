package com.example.chessapi.repository;

import com.example.chessapi.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MatchRepository extends JpaRepository<Match, UUID> {
    List<Match> findByWhitePlayerIdOrBlackPlayerIdOrderByEndedAtDesc(UUID whiteId, UUID blackId);
    List<Match> findByTournamentIdOrderByStartedAtAsc(UUID tournamentId);
    boolean existsByWhitePlayerIdAndEndedAtIsNull(UUID playerId);
    boolean existsByBlackPlayerIdAndEndedAtIsNull(UUID playerId);
    Page<Match> findByWhitePlayerIdOrBlackPlayerId(UUID whiteId, UUID blackId, Pageable pageable);
}