package nl.novi.GalacticEndgame.dtos.user;

import nl.novi.GalacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.GalacticEndgame.dtos.profile.ProfileResponseDTO;
import nl.novi.GalacticEndgame.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {
    private Long userId;
    private String username;
    private UserRole userRole;
    private String userAvatarUrl;
    private LocalDateTime createdAt;
    private ProfileResponseDTO profile;
    private List<HuntResponseDTO> hunts;

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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public ProfileResponseDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileResponseDTO profile) {
        this.profile = profile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<HuntResponseDTO> getHunts() {
        return hunts;
    }

    public void setHunts(List<HuntResponseDTO> hunts) {
        this.hunts = hunts;
    }
}
