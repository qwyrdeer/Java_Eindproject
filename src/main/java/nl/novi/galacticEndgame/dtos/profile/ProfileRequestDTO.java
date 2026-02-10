package nl.novi.galacticEndgame.dtos.profile;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfileRequestDTO {
    @Size(max=500)
    private String profileText;

    @Pattern(regexp = "^(?i)(?:https?://)?(?:www\\.)?twitch\\.tv/[A-Za-z0-9_]{4,25}(?:/?(?:\\?.*)?)?$", message = "Twitch URL must be a valid twitch.tv link")
    @Size(max = 255)
    private String twitchUrl;

    @Pattern(regexp = "^(?i)(?:https?://)?(?:www\\.)?youtube\\.com/(?:@[\\w.-]+|c/[\\w.-]+|user/[\\w.-]+|channel/[A-Za-z0-9_-]+|[\\w.-]+)(?:[?&#].*)?$", message = "YouTube URL must be a valid YouTube link")
    @Size(max = 255)
    private String youtubeUrl;

    @Pattern(regexp = "^(?i)(?:https?://)?(?:www\\.)?(?:discord\\.gg/|discord\\.com/invite/|discordapp\\.com/invite/)[A-Za-z0-9-]+(?:/?(?:\\?.*)?)?$", message = "Discord URL must be a valid Discord link")
    @Size(max = 255)
    private String discordUrl;

    // -- getter setter

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
}
