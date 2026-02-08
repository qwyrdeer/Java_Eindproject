package nl.novi.GalacticEndgame.dtos.hunt;

import jakarta.validation.constraints.*;
import nl.novi.GalacticEndgame.enums.HuntStatus;

import java.time.LocalDate;

public class HuntRequestDTO {

    @NotBlank
    private String usedGame;

    @NotBlank
    private String huntMethod;

    @Min(0)
    @Max(99999)
    private Long encounters;

    @NotNull
    private HuntStatus huntStatus;

    @PastOrPresent
    private LocalDate finishDate;

    private Long userId;

    @NotNull(message = "dexId is required")
    @Positive
    private Long dexId;
    private String name;
    private String shinyImg;

    @AssertTrue(message = "Encounters must be 1 or more than 1 when huntStatus is set to finished")
    public boolean isEncountersValidForFinished() {
        return huntStatus != HuntStatus.FINISHED || encounters >= 1;
    }

    @AssertTrue(message = "Finish date is required when hunt status is set to finished")
    public boolean isFinishDateValidForFinished() {
        return huntStatus != HuntStatus.FINISHED || finishDate != null;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
