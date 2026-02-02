package nl.novi.GalacticEndgame.dtos.pokemon;

import java.time.LocalDateTime;

public class PokemonResponseDTO {
    private Long dexId;
    private String name;
    private String shinyImg;
    private Long huntCount;
    private LocalDateTime dateFirstHunted;

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

    public String getShinyImg() {
        return shinyImg;
    }

    public void setShinyImg(String shinyImg) {
        this.shinyImg = shinyImg;
    }

    public Long getHuntCount() {
        return huntCount;
    }

    public void setHuntCount(Long huntCount) {
        this.huntCount = huntCount;
    }

    public LocalDateTime getDateFirstHunted() {
        return dateFirstHunted;
    }

    public void setDateFirstHunted(LocalDateTime dateFirstHunted) {
        this.dateFirstHunted = dateFirstHunted;
    }
}
