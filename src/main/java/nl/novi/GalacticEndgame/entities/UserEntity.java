package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class UserEntity {
    @Id
    @GeneratedValue
    private Long userId;
    private String username;
    private LocalDateTime createdAt;

    private enum userRole {USER, ADMIN}

    private ProfileEntity profileEntity;
    private ImageEntity userAvatar;

    private LocalDateTime lastLogin;

    private boolean blocked;
    private String blockReason;

    private enum blockDuration{DAY, WEEK, PERMANENT}

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ------ getters & setters


}
