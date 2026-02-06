package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.*;
import nl.novi.GalacticEndgame.dtos.pokemon.PokemonRequestDTO;
import nl.novi.GalacticEndgame.enums.HuntStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "hunts")
public class HuntEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usedGame;
    private String huntMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HuntStatus huntStatus;

    private Long encounters;

    @ManyToOne
    @JoinColumn(name = "pokemon_dex_id", referencedColumnName = "dex_id", nullable = false)
    private PokemonEntity pokemon;

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


    // toevoegen aan front-end!

    public void changeStatus(HuntStatus newStatus, LocalDate userInputFinishDate) {
        this.huntStatus = newStatus;
        if (newStatus == HuntStatus.FINISHED) {
            if (userInputFinishDate == null) {
                throw new IllegalArgumentException("finish date is required when finishing a hunt");
            }
            this.finishDate = userInputFinishDate;
            this.finishedHunt = LocalDateTime.now();
        }
    }

    // ------ getters & setters


    public LocalDateTime getFinishedHunt() {
        return finishedHunt;
    }

    public void setFinishedHunt(LocalDateTime finishedHunt) {
        this.finishedHunt = finishedHunt;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public PokemonEntity getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonEntity pokemon) {
        this.pokemon = pokemon;
    }

    public Long getEncounters() {
        return encounters;
    }

    public void setEncounters(Long encounters) {
        this.encounters = encounters;
    }

    public HuntStatus getHuntStatus() {
        return huntStatus;
    }

    public void setHuntStatus(HuntStatus huntStatus) {
        this.huntStatus = huntStatus;
    }

    public String getHuntMethod() {
        return huntMethod;
    }

    public void setHuntMethod(String huntMethod) {
        this.huntMethod = huntMethod;
    }

    public String getUsedGame() {
        return usedGame;
    }

    public void setUsedGame(String usedGame) {
        this.usedGame = usedGame;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity userEntity) {
    }

    public UserEntity getUser() {
        return userEntity;
    }
}
