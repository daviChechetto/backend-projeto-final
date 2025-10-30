package com.chessapi.service;

import com.chessapi.dto.MatchDTO;
import com.chessapi.model.Match;
import com.chessapi.model.Player;
import com.chessapi.model.Tournament;
import com.chessapi.repository.MatchRepository;
import com.chessapi.repository.PlayerRepository;
import com.chessapi.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepo;
    private final PlayerRepository playerRepo;
    private final TournamentRepository tournamentRepo;

    public MatchService(MatchRepository matchRepo, PlayerRepository playerRepo, TournamentRepository tournamentRepo) {
        this.matchRepo = matchRepo;
        this.playerRepo = playerRepo;
        this.tournamentRepo = tournamentRepo;
    }

    public List<Match> listAll(String result, String orderBy) {
        List<Match> matches = matchRepo.findAll();

        if (result != null) {
            matches = matches.stream()
                    .filter(m -> result.equalsIgnoreCase(m.getResult()))
                    .toList();
        }

        if ("createdAt".equalsIgnoreCase(orderBy)) {
            matches = matches.stream()
                    .sorted(Comparator.comparing(Match::getCreatedAt))
                    .toList();
        } else if ("updatedAt".equalsIgnoreCase(orderBy)) {
            matches = matches.stream()
                    .sorted(Comparator.comparing(Match::getUpdatedAt))
                    .toList();
        }

        return matches;
    }

    @Transactional
    public Match create(MatchDTO dto) {
        Player white = playerRepo.findById(dto.playerWhiteId)
                .orElseThrow(() -> new RuntimeException("Jogador branco não encontrado"));
        Player black = playerRepo.findById(dto.playerBlackId)
                .orElseThrow(() -> new RuntimeException("Jogador preto não encontrado"));

        Match match = new Match(white, black, dto.moves);

        if (dto.tournamentId != null) {
            Tournament t = tournamentRepo.findById(dto.tournamentId)
                    .orElseThrow(() -> new RuntimeException("Torneio não encontrado"));
            match.setTournament(t);
        }

        return matchRepo.save(match);
    }

    public Match getById(UUID id) {
        return matchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));
    }

    @Transactional
    public Match update(UUID id, MatchDTO dto) {
        Match match = getById(id);

        if (dto.result != null && !List.of("white", "black", "draw").contains(dto.result))
            throw new RuntimeException("Resultado inválido");

        if (dto.moves != null)
            match.setMoves(dto.moves);

        if (dto.result != null) {
            match.setResult(dto.result);
            applyEloChange(match, dto.result);
        }

        match.setUpdatedAt(LocalDateTime.now());
        return matchRepo.save(match);
    }

    private void applyEloChange(Match match, String result) {
        Player white = match.getPlayerWhite();
        Player black = match.getPlayerBlack();

        double Rw = Math.pow(10, white.getElo() / 400.0);
        double Rb = Math.pow(10, black.getElo() / 400.0);
        double Ew = Rw / (Rw + Rb);
        double Eb = Rb / (Rw + Rb);

        double Sw = switch (result) {
            case "white" -> 1;
            case "black" -> 0;
            default -> 0.5;
        };
        double Sb = 1 - Sw;

        if ("white".equals(result)) match.setWinner(white);
        else if ("black".equals(result)) match.setWinner(black);
        else match.setWinner(null);

        int K = 20;
        white.setElo((int) Math.round(white.getElo() + K * (Sw - Ew)));
        black.setElo((int) Math.round(black.getElo() + K * (Sb - Eb)));

        playerRepo.save(white);
        playerRepo.save(black);
    }

    public void delete(UUID id) {
        if (!matchRepo.existsById(id))
            throw new RuntimeException("Partida não encontrada");
        matchRepo.deleteById(id);
    }
}
