package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class ProfileEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private UserEntity user;

    private String profileText;
    private String twitchUrl;
    private String discordUrl;
    private String youtubeUrl;

    // ------ getters & setters

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getDiscordUrl() {
        return discordUrl;
    }

    public void setDiscordUrl(String discordUrl) {
        this.discordUrl = discordUrl;
    }

    public String getTwitchUrl() {
        return twitchUrl;
    }

    public void setTwitchUrl(String twitchUrl) {
        this.twitchUrl = twitchUrl;
    }

    public String getProfileText() {
        return profileText;
    }

    public void setProfileText(String profileText) {
        this.profileText = profileText;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
