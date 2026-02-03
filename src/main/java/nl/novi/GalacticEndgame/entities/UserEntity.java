package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.*;
import nl.novi.GalacticEndgame.enums.BlockDuration;
import nl.novi.GalacticEndgame.enums.UserRole;

import java.time.LocalDateTime;

public class UserEntity {
    @Id
    @GeneratedValue
    private Long userId;
    private String username;
    protected LocalDateTime createdAt;

    @Column(name = "edited_date")
    private LocalDateTime editedAt;

    @Enumerated
    private UserRole userRole;

    private ProfileEntity profileEntity;
    private ImageEntity userAvatar;

    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private boolean blocked = false;
    private LocalDateTime blockedAt;
    private String blockReason;
    private LocalDateTime blockedUntil;

    @Enumerated
    private BlockDuration duration;

    public void block(BlockDuration duration, String reason) {
        this.blocked = true;

        LocalDateTime now = LocalDateTime.now();
        this.blockedAt = now;
        this.blockReason = reason;

        this.blockedUntil = switch (duration) {
            case DAY -> now.plusDays(1);
            case WEEK -> now.plusDays(7);
            case PERMANENT -> null;
        };
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        editedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        editedAt = LocalDateTime.now();
    }

    // ------ getters & setters


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public ProfileEntity getProfileEntity() {
        return profileEntity;
    }

    public void setProfileEntity(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
        if (profileEntity != null) {
            profileEntity.setUser(this);
        }
    }

    public ImageEntity getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(ImageEntity userAvatar) {
        this.userAvatar = userAvatar;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }

    public void setBlockedAt(LocalDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public LocalDateTime getBlockedUntil() {
        return blockedUntil;
    }

    public void setBlockedUntil(LocalDateTime blockedUntil) {
        this.blockedUntil = blockedUntil;
    }

    public BlockDuration getDuration() {
        return duration;
    }

    public void setDuration(BlockDuration duration) {
        this.duration = duration;
    }
}
