package nl.novi.GalacticEndgame.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pokemon")
public class PokemonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dex_id", unique = true, nullable = false)
    private Long dexId;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pkmn_gif")
    @JsonIgnoreProperties(value = "contentType")
    private ImageEntity shinyImg;

    @Column(name = "date_first_hunted", updatable = false)
    private LocalDateTime dateFirstHunted;

    @Column(name = "hunt_count", nullable = false)
    private Long huntCount = 0L;

    public void registerHunt() {
        huntCount++;
    }

    // ------ getters & setters

}
