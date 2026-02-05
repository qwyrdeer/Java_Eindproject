package nl.novi.GalacticEndgame.dtos.profile;

import nl.novi.GalacticEndgame.dtos.user.UserExtendedDTO;

public class ProfileExtendedDTO {
    private String profileText;
    private String twitchUrl;
    private String youtubeUrl;
    private String discordUrl;

    private UserExtendedDTO user;

    public String getProfileText() {
        return profileText;
    }

    public void setProfileText(String profileText) {
        this.profileText = profileText;
    }

    public String getTwitchUrl() {
        return twitchUrl;
    }

    public void setTwitchUrl(String twitchUrl) {
        this.twitchUrl = twitchUrl;
    }

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

    public UserExtendedDTO getUser() {
        return user;
    }

    public void setUser(UserExtendedDTO user) {
    this.user = user;
    }
}
