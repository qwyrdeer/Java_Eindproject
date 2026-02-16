package nl.novi.galacticEndgame;

import jakarta.transaction.Transactional;
import nl.novi.galacticEndgame.entities.ImageEntity;
import nl.novi.galacticEndgame.enums.ImageType;
import nl.novi.galacticEndgame.repositories.ImageRepository;
import nl.novi.galacticEndgame.services.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional

public class ImageIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;
    @Value("${my.upload_location}")
    private String uploadLocation;

    @Test
    void storeImageShouldSaveImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "file",
                "avatar.png",
                "image/png",
                "test-image-content".getBytes()
        );

        ImageEntity saved = imageService.storeImage(image, ImageType.AVATAR);

        assertNotNull(saved.getId());
        assertEquals("avatar.png", saved.getOriginalName());
        assertEquals("image/png", saved.getContentType());

        Path file = Paths.get(uploadLocation)
                    .resolve("avatars")
                    .resolve(saved.getStoredName());

        assertTrue(Files.exists(file));

    }

}
