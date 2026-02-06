package nl.novi.GalacticEndgame.dtos.user;

import nl.novi.GalacticEndgame.dtos.profile.ProfileExtendedDTO;
import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.enums.UserRole;

public class UserResponseDTO {
    private Long userId;
    private String username;
    private UserRole userRole;
    private ImageEntity userAvatar;

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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public ImageEntity getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(ImageEntity userAvatar) {
        this.userAvatar = userAvatar;
    }

    public ProfileExtendedDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileExtendedDTO profile) {
        this.profile = profile;
    }
}
