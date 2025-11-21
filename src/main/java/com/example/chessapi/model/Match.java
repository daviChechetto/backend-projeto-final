package com.example.chessapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "matches")
public class Match {
    public enum Result { WHITE_WIN, BLACK_WIN, DRAW }

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID whitePlayerId;

    @Column(nullable = false)
    private UUID blackPlayerId;

    @Enumerated(EnumType.STRING)
    private Result result;

    private Integer whiteRatingBefore;
    private Integer blackRatingBefore;
    private Integer whiteRatingAfter;
    private Integer blackRatingAfter;

    @Lob
    private String moves; // PGN simples ou SAN concatenado

    private UUID tournamentId;

    @Column(nullable = false)
    private String timeControl; // "10+0"

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}