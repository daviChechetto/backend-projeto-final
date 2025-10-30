package com.chessapi.service;

import com.chessapi.dto.MatchDTO;
import com.chessapi.model.Match;
import com.chessapi.model.Player;
import com.chessapi.model.Tournament;
import com.chessapi.repository.MatchRepository;
import com.chessapi.repository.PlayerRepository;
import com.chessapi.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;

    public MatchService(
            MatchRepository matchRepository,
            PlayerRepository playerRepository,
            TournamentRepository tournamentRepository
    ) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public List<Match> listAll(String result, String orderBy) {
        List<Match> matches;

        if (result != null && !result.isBlank()) {
            matches = matchRepository.findByResult(result);
        } else {
            matches = matchRepository.findAll();
        }

        if (orderBy != null) {
            switch (orderBy) {
                case "createdAt" -> matches.sort(Comparator.comparing(Match::getCreatedAt));
                case "updatedAt" -> matches.sort(Comparator.comparing(Match::getUpdatedAt));
                case "result" -> matches.sort(Comparator.comparing(Match::getResult));
            }
        }

        return matches;
    }

    public Match getById(UUID id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada com ID: " + id));
    }

    public Match create(MatchDTO dto) {
        Player white = playerRepository.findById(dto.playerWhiteId)
                .orElseThrow(() -> new RuntimeException("Jogador branco não encontrado."));
        Player black = playerRepository.findById(dto.playerBlackId)
                .orElseThrow(() -> new RuntimeException("Jogador preto não encontrado."));

        Tournament tournament = null;
        if (dto.tournamentId != null) {
            tournament = tournamentRepository.findById(dto.tournamentId)
                    .orElseThrow(() -> new RuntimeException("Torneio não encontrado."));
        }

        Match match = new Match();
        match.setPlayerWhite(white);
        match.setPlayerBlack(black);
        match.setTournament(tournament);
        match.setMoves(dto.moves);
        match.setResult(dto.result != null ? dto.result : "ongoing");
        match.setCreatedAt(LocalDateTime.now());
        match.setUpdatedAt(LocalDateTime.now());

        return matchRepository.save(match);
    }

    public Match update(UUID id, MatchDTO dto) {
        Match match = getById(id);

        if (dto.playerWhiteId != null) {
            Player white = playerRepository.findById(dto.playerWhiteId)
                    .orElseThrow(() -> new RuntimeException("Jogador branco não encontrado."));
            match.setPlayerWhite(white);
        }

        if (dto.playerBlackId != null) {
            Player black = playerRepository.findById(dto.playerBlackId)
                    .orElseThrow(() -> new RuntimeException("Jogador preto não encontrado."));
            match.setPlayerBlack(black);
        }

        if (dto.tournamentId != null) {
            Tournament tournament = tournamentRepository.findById(dto.tournamentId)
                    .orElseThrow(() -> new RuntimeException("Torneio não encontrado."));
            match.setTournament(tournament);
        }

        if (dto.moves != null) match.setMoves(dto.moves);
        if (dto.result != null) match.setResult(dto.result);

        if (dto.result != null) {
            switch (dto.result) {
                case "white" -> match.setWinner(match.getPlayerWhite());
                case "black" -> match.setWinner(match.getPlayerBlack());
                default -> match.setWinner(null);
            }
        }

        match.setUpdatedAt(LocalDateTime.now());
        return matchRepository.save(match);
    }

    public void delete(UUID id) {
        if (!matchRepository.existsById(id)) {
            throw new RuntimeException("Partida não encontrada para exclusão.");
        }
        matchRepository.deleteById(id);
    }
}
