package nl.novi.galacticEndgame.dtos.pokemon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.novi.galacticEndgame.entities.ImageEntity;

import java.time.LocalDateTime;

public class PokemonRequestDTO {
    private static final long MAX_BYTES = 2_000_000;
//    private Long id;

    @NotBlank
    @Schema(example = "150")
    @Size(min = 1, max = 1025, message = "Dex id must be between 1 and 1025 (for now)")
    private Long dexId;

    @NotBlank
    @Schema(example = "Togepi")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @NotNull
    @Max(value = MAX_BYTES, message = "size must be <= 2MB")
    private ImageEntity shinyImg;

    private LocalDateTime dateFirstHunted;
    private Long huntCount;

    // ----- getters en setters


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

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
