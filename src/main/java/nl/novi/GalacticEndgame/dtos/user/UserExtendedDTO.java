package nl.novi.GalacticEndgame.dtos.user;

import nl.novi.GalacticEndgame.dtos.profile.ProfileExtendedDTO;
import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.enums.UserRole;

import java.time.LocalDateTime;

public class UserExtendedDTO {

    private Long userId;
    private String username;

    private ImageEntity userAvatar;
    private UserRole userRole;

    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    private boolean blocked;
    private LocalDateTime blockedUntil;
    private String blockReason;

    private ProfileExtendedDTO profile;

// -- getter en setter

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

    public ImageEntity getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(ImageEntity userAvatar) {
        this.userAvatar = userAvatar;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public LocalDateTime getBlockedUntil() {
        return blockedUntil;
    }

    public void setBlockedUntil(LocalDateTime blockedUntil) {
        this.blockedUntil = blockedUntil;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public ProfileExtendedDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileExtendedDTO profile) {
        this.profile = profile;
    }
}
