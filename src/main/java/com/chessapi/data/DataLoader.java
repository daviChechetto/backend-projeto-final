package com.chessapi.data;

import com.chessapi.model.Match;
import com.chessapi.model.Player;
import com.chessapi.model.Tournament;
import com.chessapi.repository.MatchRepository;
import com.chessapi.repository.PlayerRepository;
import com.chessapi.repository.TournamentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final PlayerRepository playerRepo;
    private final MatchRepository matchRepo;
    private final TournamentRepository tournamentRepo;

    public DataLoader(PlayerRepository playerRepo, MatchRepository matchRepo, TournamentRepository tournamentRepo) {
        this.playerRepo = playerRepo;
        this.matchRepo = matchRepo;
        this.tournamentRepo = tournamentRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (playerRepo.count() == 0) {
            Player p1 = new Player("Magnus Carlsen", "magnus@magnus.com", "secret", "NOR");
            Player p2 = new Player("Hikaru Nakamura", "hikaru@hikaru.com", "secret", "USA");
            Player p3 = new Player("Ian Nepomniachtchi", "ian@ian.com", "secret", "RUS");
            playerRepo.save(p1);
            playerRepo.save(p2);
            playerRepo.save(p3);

            Tournament t1 = new Tournament("Open Example 2025", "Online", LocalDate.now().minusDays(10), LocalDate.now().plusDays(5));
            tournamentRepo.save(t1);

            Match m1 = new Match(p1, p2, "1. e4 e5 2. Nf3 Nc6 3. Bb5 a6");
            m1.setTournament(t1);
            matchRepo.save(m1);

            Match m2 = new Match(p2, p3, "1. d4 d5 2. c4 c6");
            matchRepo.save(m2);
        }
    }
}
