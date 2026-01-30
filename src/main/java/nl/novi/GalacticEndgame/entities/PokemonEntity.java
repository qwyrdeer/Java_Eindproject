package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

public class PokemonEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long dexId;
    private String name;

    @OneToOne
    private ImageEntity shinyImg;

    private LocalDateTime dateFirstHunted;
    private Long huntCount;


    // ------ getters & setters

}
