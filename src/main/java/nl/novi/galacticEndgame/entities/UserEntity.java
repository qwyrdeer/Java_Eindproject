package nl.novi.galacticEndgame.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import nl.novi.galacticEndgame.enums.BlockDuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Column(unique = true, nullable = false)
    private String kcid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, length = 25)
    private String username;

    @Column(name = "create_date", nullable = false)
    protected LocalDateTime createdAt;

    @Column(name = "edited_date")
    private LocalDateTime editedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private ProfileEntity profileEntity;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "contentType")
    @JoinColumn(name = "avatar_image")
    private ImageEntity userAvatar;

    @OneToMany(mappedBy = "userEntity")
    private List<HuntEntity> hunts = new ArrayList<>();

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "user_role")
    private String userRole;

    @Column(nullable = false)
    private boolean blocked = false;
    private LocalDateTime blockedAt;
    @Column(length = 500)
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid;
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

    public ProfileEntity getProfile() {
        return profileEntity;
    }

    public void setProfile(ProfileEntity profileEntity) {
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

    public List<HuntEntity> getHunts() {
        return hunts;
    }

    public void setHunts(List<HuntEntity> hunts) {
        this.hunts = hunts;
    }
}
