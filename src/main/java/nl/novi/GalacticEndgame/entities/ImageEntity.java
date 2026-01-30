package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class ImageEntity {
@Id
@GeneratedValue
private Long id;

private String originalName;
private String storedName;
private String url;
private String contentType;

    private enum ImageType{AVATAR, PKMN_GIF}

private Long size;

private LocalDateTime createdAt;

    // ------ getters & setters


}
