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

}
