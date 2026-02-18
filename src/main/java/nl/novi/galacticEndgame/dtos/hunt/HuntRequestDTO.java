package nl.novi.galacticEndgame.dtos.hunt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import nl.novi.galacticEndgame.enums.HuntStatus;

import java.time.LocalDate;

@Schema(description = "Request for creating a hunt")
public class HuntRequestDTO {

    @NotBlank
    @Schema(example = "Scarlet")
    private String usedGame;

    @NotBlank
    @Schema(example = "Random encounters")
    private String huntMethod;

    @Min(0)
    @Max(99999)
    @Schema(example = "1000")
    private Long encounters;

    @NotNull
    private HuntStatus huntStatus;

    @PastOrPresent
    @Schema(example = "27-02-2026")
    private LocalDate finishDate;

    @Schema(example = "150")
    @NotNull(message = "dexId is required")
    @Positive
    private Long dexId;

    @Schema(example = "Mewtwo")
    private String name;
    private String shinyImg;

    @AssertTrue(message = "Encounters must be 1 or more than 1 when huntStatus is set to finished")
    public boolean isEncountersValidForFinished() {
        if (huntStatus == HuntStatus.FINISHED) {
            return encounters != null && encounters >= 1;
        }
        return true;
    }

    @AssertTrue(message = "Finish date is required when hunt status is set to finished")
    public boolean isFinishDateValidForFinished() {
        if (huntStatus == HuntStatus.FINISHED) {
            return finishDate != null;
        }
        return true;
    }

    // -- getters en setters

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

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
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

    public String getShinyImg() {
        return shinyImg;
    }

    public void setShinyImg(String shinyImg) {
        this.shinyImg = shinyImg;
    }

}
