package nl.novi.galacticEndgame.dtos.user;

import nl.novi.galacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.galacticEndgame.dtos.profile.ProfileResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {
    private Long userId;
    private String username;
    private String userAvatarUrl;
    private LocalDateTime createdAt;
    private String userRole;
    private ProfileResponseDTO profile;
    private List<HuntResponseDTO> hunts;

    // -- getter en setter

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

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
