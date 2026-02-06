package nl.novi.GalacticEndgame.dtos.hunt;

import nl.novi.GalacticEndgame.dtos.pokemon.PokemonResponseDTO;
import nl.novi.GalacticEndgame.entities.PokemonEntity;
import nl.novi.GalacticEndgame.entities.UserEntity;
import nl.novi.GalacticEndgame.enums.HuntStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HuntResponseDTO {

    private Long id;
    private String usedGame;
    private String huntMethod;
    private HuntStatus huntStatus;
    private Long encounters;
    private PokemonResponseDTO pokemon;
    private Long userId;
    private LocalDateTime createDate;
    private LocalDate finishDate;
    private LocalDateTime editDate;
    private LocalDateTime finishedHunt;

    // ---- getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsedGame() {
        return usedGame;
    }

    public void setUsedGame(String usedGame) {
        this.usedGame = usedGame;
    }

    public String getHuntMethod() {
        return huntMethod;
    }

    public void setHuntMethod(String huntMethod) {
        this.huntMethod = huntMethod;
    }

    public HuntStatus getHuntStatus() {
        return huntStatus;
    }

    public void setHuntStatus(HuntStatus huntStatus) {
        this.huntStatus = huntStatus;
    }

    public Long getEncounters() {
        return encounters;
    }

    public void setEncounters(Long encounters) {
        this.encounters = encounters;
    }

    public PokemonResponseDTO getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonResponseDTO pokemon) {
        this.pokemon = pokemon;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public LocalDateTime getFinishedHunt() {
        return finishedHunt;
    }

    public void setFinishedHunt(LocalDateTime finishedHunt) {
        this.finishedHunt = finishedHunt;
    }
}
