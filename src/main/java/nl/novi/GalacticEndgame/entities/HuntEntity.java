package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HuntEntity {
    @Id
    @GeneratedValue
private Long id;
private String usedGame;
private String huntMethod;

private enum huntStatus{FINISHED, CURRENT, FUTURE}

private Long encounters;

@ManyToOne
private PokemonEntity pokemonEntity;

@ManyToOne
private UserEntity userEntity;

    private LocalDateTime createDate;
    private LocalDate finishDate;
    private LocalDateTime editDate;
    private LocalDateTime finishedHunt;

    // ------ getters & setters

}
