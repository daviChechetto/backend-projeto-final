package com.chessapi.service;

import com.chessapi.dto.TournamentDTO;
import com.chessapi.model.Tournament;
import com.chessapi.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TournamentService {

    private final TournamentRepository repo;

    public TournamentService(TournamentRepository repo) {
        this.repo = repo;
    }

    public List<Tournament> listAll() {
        return repo.findAll();
    }

    public Tournament create(TournamentDTO dto) {
        Tournament t = new Tournament(
                dto.name,
                dto.location,
                dto.startDate,
                dto.endDate
        );
        return repo.save(t);
    }

    public Tournament getById(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
    }

    public Tournament update(UUID id, TournamentDTO dto) {
        Tournament t = getById(id);
        t.setName(dto.name);
        t.setLocation(dto.location);
        t.setStartDate(dto.startDate);
        t.setEndDate(dto.endDate);
        return repo.save(t);
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Tournament not found");
        }
        repo.deleteById(id);
    }
}
