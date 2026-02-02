package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import nl.novi.GalacticEndgame.enums.BlockDuration;
import nl.novi.GalacticEndgame.enums.UserRole;

import java.time.LocalDateTime;

public class UserEntity {
    @Id
    @GeneratedValue
    private Long userId;
    private String username;
    private LocalDateTime createdAt;

    @Enumerated
    private UserRole userRole;

    private ProfileEntity profileEntity;
    private ImageEntity userAvatar;

    private LocalDateTime lastLogin;

    private boolean blocked;
    private String blockReason;

    @Enumerated
    private BlockDuration blockDuration;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ------ getters & setters


}
