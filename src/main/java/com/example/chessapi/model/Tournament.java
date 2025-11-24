package com.example.chessapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "tournaments")
public class Tournament implements Serializable {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String format; // SWISS, ROUND_ROBIN, KNOCKOUT

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Integer maxPlayers = 32;

    @ElementCollection
    @CollectionTable(name = "tournament_participants", joinColumns = @JoinColumn(name = "tournament_id"))
    @Column(name = "player_id")
    private List<UUID> participantIds = new ArrayList<>();

    @Column(nullable = false)
    private String status = "PLANNED";

    @Column(nullable = false)
    private UUID ownerId;

    private UUID winnerId;
}

