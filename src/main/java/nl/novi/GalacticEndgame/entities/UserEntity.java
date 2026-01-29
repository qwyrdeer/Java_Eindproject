package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class UserEntity {

    private Long userId;
    private String username;
    private LocalDateTime createDate;

    // ENUM van maken?
    private String userRole;

    private ProfileEntity profileEntity;
    private ImageEntity userAvatar;

    private LocalDateTime lastLogin;

    private boolean blocked;
    private String blockReason;

    private enum blockDuration{}

    @PrePersist
    public void onCreate() {
        this.createDate = LocalDateTime.now();
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public ProfileEntity getProfileEntity() {
        return profileEntity;
    }

    public void setProfileEntity(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
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

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }
}
