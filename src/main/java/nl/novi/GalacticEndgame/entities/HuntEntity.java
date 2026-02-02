package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.*;
import nl.novi.GalacticEndgame.enums.HuntStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "hunts")
public class HuntEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String usedGame;
    private String huntMethod;

    @Enumerated
    @Column(nullable = false)
    private HuntStatus huntStatus;

    private Long encounters;

    @ManyToOne
    @JoinColumn(name = "pokemon_dex_id", referencedColumnName = "dex_id", nullable = false)
    private PokemonEntity pokemonEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @UpdateTimestamp
    private LocalDateTime editDate;

    @Column(name = "finished_hunt")
    private LocalDateTime finishedHunt;

    // ------ getters & setters

}
