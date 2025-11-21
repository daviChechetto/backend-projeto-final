package com.example.chessapi.service;

import com.example.chessapi.dto.MatchCreateDto;
import com.example.chessapi.dto.MatchFinishDto;
import com.example.chessapi.dto.MatchPgnDto;
import com.example.chessapi.model.Match;
import com.example.chessapi.model.Player;
import com.example.chessapi.model.Tournament;
import com.example.chessapi.repository.MatchRepository;
import com.example.chessapi.repository.PlayerRepository;
import com.example.chessapi.repository.TournamentRepository;
import com.example.chessapi.util.EloCalculator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepo;
    private final PlayerRepository playerRepo;
    private final TournamentRepository tournamentRepo;

    @Transactional
    public Match create(MatchCreateDto dto) {
        if (dto.getWhitePlayerId().equals(dto.getBlackPlayerId())) {
            throw new IllegalArgumentException("Players must be different");
        }

        Player white = playerRepo.findById(dto.getWhitePlayerId())
                .orElseThrow(() -> new EntityNotFoundException("White player not found"));
        Player black = playerRepo.findById(dto.getBlackPlayerId())
                .orElseThrow(() -> new EntityNotFoundException("Black player not found"));

        boolean whiteBusy = matchRepo.existsByWhitePlayerIdAndEndedAtIsNull(white.getId())
                || matchRepo.existsByBlackPlayerIdAndEndedAtIsNull(white.getId());
        boolean blackBusy = matchRepo.existsByWhitePlayerIdAndEndedAtIsNull(black.getId())
                || matchRepo.existsByBlackPlayerIdAndEndedAtIsNull(black.getId());

        if (whiteBusy || blackBusy) {
            throw new IllegalStateException("Player already in an active match");
        }

        if (!white.getActive() || !black.getActive()) {
            throw new IllegalStateException("Inactive player cannot play");
        }

        if (dto.getTournamentId() != null) {
            Tournament t = tournamentRepo.findById(dto.getTournamentId())
                    .orElseThrow(() -> new EntityNotFoundException("Tournament not found"));

            if (!t.getParticipantIds().contains(white.getId()) || !t.getParticipantIds().contains(black.getId())) {
                throw new IllegalStateException("Players must be registered in the tournament");
            }

            if (!"ONGOING".equals(t.getStatus())) {
                throw new IllegalStateException("Tournament must be started to create matches");
            }
        }

        Match m = Match.builder()
                .whitePlayerId(white.getId())
                .blackPlayerId(black.getId())
                .timeControl(dto.getTimeControl())
                .tournamentId(dto.getTournamentId())
                .startedAt(LocalDateTime.now())
                .build();
        return matchRepo.save(m);
    }

    public Match get(UUID id) {
        return matchRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Match not found"));
    }

    public List<Match> list() {
        return matchRepo.findAll();
    }

    @Transactional
    public Match updatePgn(UUID id, MatchPgnDto dto) {
        Match m = get(id);

        if (m.getEndedAt() != null) {
            throw new IllegalStateException("Cannot update PGN of a finished match");
        }

        m.setMoves(dto.getMoves());
        return matchRepo.save(m);
    }

    @Transactional
    public Match finish(UUID id, MatchFinishDto dto) {
        Match m = get(id);

        if (m.getEndedAt() != null) {
            throw new IllegalStateException("Match is already finished");
        }

        Match.Result result = Match.Result.valueOf(dto.getResult().toUpperCase());

        Player white = playerRepo.findById(m.getWhitePlayerId())
                .orElseThrow(() -> new EntityNotFoundException("White player not found"));
        Player black = playerRepo.findById(m.getBlackPlayerId())
                .orElseThrow(() -> new EntityNotFoundException("Black player not found"));

        m.setWhiteRatingBefore(white.getRating());
        m.setBlackRatingBefore(black.getRating());

        if (m.getTournamentId() != null) {
            m.setWhiteRatingAfter(white.getRating());
            m.setBlackRatingAfter(black.getRating());
        } else {
            double sWhite = switch (result) {
                case WHITE_WIN -> 1.0;
                case DRAW -> 0.5;
                case BLACK_WIN -> 0.0;
            };
            double sBlack = 1.0 - sWhite;

            int whiteNew = EloCalculator.newRating(white.getRating(), black.getRating(), sWhite);
            int blackNew = EloCalculator.newRating(black.getRating(), white.getRating(), sBlack);

            m.setWhiteRatingAfter(whiteNew);
            m.setBlackRatingAfter(blackNew);

            white.setRating(whiteNew);
            black.setRating(blackNew);

            switch (result) {
                case WHITE_WIN -> {
                    white.setWins(white.getWins() + 1);
                    black.setLosses(black.getLosses() + 1);
                }
                case BLACK_WIN -> {
                    black.setWins(black.getWins() + 1);
                    white.setLosses(white.getLosses() + 1);
                }
                case DRAW -> {
                    white.setDraws(white.getDraws() + 1);
                    black.setDraws(black.getDraws() + 1);
                }
            }

            playerRepo.save(white);
            playerRepo.save(black);
        }

        m.setResult(result);
        m.setEndedAt(LocalDateTime.now());
        return matchRepo.save(m);
    }

    @Transactional
    public void cancel(UUID id) {
        Match m = get(id);
        if (m.getEndedAt() != null) throw new IllegalStateException("Cannot cancel finished match");
        matchRepo.delete(m);
    }

    public List<Match> historyForPlayer(UUID playerId) {
        return matchRepo.findByWhitePlayerIdOrBlackPlayerIdOrderByEndedAtDesc(playerId, playerId);
    }
}
