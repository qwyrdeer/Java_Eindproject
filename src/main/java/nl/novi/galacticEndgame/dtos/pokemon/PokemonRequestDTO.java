package nl.novi.galacticEndgame.dtos.pokemon;

import nl.novi.galacticEndgame.entities.ImageEntity;

import java.time.LocalDateTime;

public class PokemonRequestDTO {
    private Long id;
    private Long dexId;
    private String name;
    private ImageEntity shinyImg;
    private LocalDateTime dateFirstHunted;
    private Long huntCount;

    // ----- getters en setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDexId() {
        return dexId;
    }

    public void setDexId(Long dexId) {
        this.dexId = dexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageEntity getShinyImg() {
        return shinyImg;
    }

    public void setShinyImg(ImageEntity shinyImg) {
        this.shinyImg = shinyImg;
    }

    public LocalDateTime getDateFirstHunted() {
        return dateFirstHunted;
    }

    public void setDateFirstHunted(LocalDateTime dateFirstHunted) {
        this.dateFirstHunted = dateFirstHunted;
    }

    public Long getHuntCount() {
        return huntCount;
    }

    public void setHuntCount(Long huntCount) {
        this.huntCount = huntCount;
    }
}
