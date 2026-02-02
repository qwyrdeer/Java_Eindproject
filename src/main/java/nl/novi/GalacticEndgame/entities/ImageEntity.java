package nl.novi.GalacticEndgame.entities;

import jakarta.persistence.*;
import nl.novi.GalacticEndgame.enums.ImageType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false, unique = true)
    private String storedName;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String contentType;

    @Enumerated
    private ImageType imageType;

    private Long size;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ------ getters & setters


}
