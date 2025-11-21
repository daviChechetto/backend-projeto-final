package com.example.chessapi.service;

import com.example.chessapi.dto.TournamentCreateDto;
import com.example.chessapi.dto.TournamentFinishDto;
import com.example.chessapi.model.Match;
import com.example.chessapi.model.Player;
import com.example.chessapi.model.Tournament;
import com.example.chessapi.repository.MatchRepository;
import com.example.chessapi.repository.PlayerRepository;
import com.example.chessapi.repository.TournamentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentService {
    private final TournamentRepository tournamentRepo;
    private final PlayerRepository playerRepo;
    private final MatchRepository matchRepo;

    @Transactional
    public Tournament create(TournamentCreateDto dto) {
        Player owner = playerRepo.findById(dto.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        if (!owner.getActive()) throw new IllegalStateException("Owner is inactive and cannot create a tournament");
        if (dto.getMaxPlayers() < 3) throw new IllegalStateException("A minimum of 3 players is required for a tournament");
        if (dto.getMaxPlayers() > 128) throw new IllegalStateException("The maximum number of players for a tournament is 128");

        Tournament t = Tournament.builder()
                .name(dto.getName())
                .format(dto.getFormat())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .maxPlayers(dto.getMaxPlayers())
                .ownerId(dto.getOwnerId())
                .status("PLANNED")
                .build();
        return tournamentRepo.save(t);
    }

    public Tournament get(UUID id) {
        return tournamentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
    }

    public List<Tournament> list() { return tournamentRepo.findAll(); }

    @Transactional
    public Tournament join(UUID id, UUID playerId) {
        Tournament t = get(id);
        Player player = playerRepo.findById(playerId).orElseThrow(() -> new EntityNotFoundException("Player not found"));

        if (!player.getActive()) throw new IllegalStateException("Player is inactive and cannot join in a tournament");
        if (!"PLANNED".equals(t.getStatus())) throw new IllegalStateException("Tournament not open");
        if (t.getParticipantIds().contains(playerId)) return t;
        if (t.getParticipantIds().size() >= t.getMaxPlayers()) throw new IllegalStateException("Tournament is full");

        t.getParticipantIds().add(playerId);

        return tournamentRepo.save(t);
    }

    @Transactional
    public Tournament start(UUID id) {
        Tournament t = get(id);

        if (!"PLANNED".equals(t.getStatus())) throw new IllegalStateException("Invalid state");
        if (t.getParticipantIds() == null || t.getParticipantIds().size() < 3) throw new IllegalStateException("Tournament must have at least 3 participants to start");

        t.setStatus("ONGOING");
        t.setStartDate(LocalDateTime.now());

        return tournamentRepo.save(t);
    }

    @Transactional
    public Tournament finish(UUID id, TournamentFinishDto dto) {
        Tournament t = get(id);

        if (!"ONGOING".equals(t.getStatus())) throw new IllegalStateException("Tournament must be ongoing to be finished");
        if (t.getParticipantIds() == null || !t.getParticipantIds().contains(dto.getWinnerId())) throw new IllegalStateException("Winner must be a participant of the tournament");

        t.setStatus("FINISHED");
        t.setEndDate(LocalDateTime.now());
        t.setWinnerId(dto.getWinnerId());

        return tournamentRepo.save(t);
    }


    public List<Match> matches(UUID id) {
        return matchRepo.findByTournamentIdOrderByStartedAtAsc(id);
    }

    @Transactional
    public void delete(UUID id) {
        Tournament t = get(id);
        if (!t.getParticipantIds().isEmpty()) throw new IllegalStateException("Cannot delete tournament with participants");
        tournamentRepo.delete(t);
    }
}