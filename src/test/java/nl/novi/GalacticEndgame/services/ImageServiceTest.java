package nl.novi.GalacticEndgame.services;

import nl.novi.GalacticEndgame.enums.ImageType;
import nl.novi.GalacticEndgame.exeptions.IncorrectInputException;
import nl.novi.GalacticEndgame.repositories.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    ImageRepository imageRepository;

    ImageService imageService;

    @BeforeEach
    void setup() {
        imageService = new ImageService("/tmp/uploads", imageRepository);
    }

    @Test
    void uploadAvatarWithWrongTypeThrowsException() {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("file/pdf");
        when(file.getOriginalFilename()).thenReturn("virus.pdf");

        //Act en Assert
        assertThrows(IncorrectInputException.class, () -> imageService.storeImage(file, ImageType.AVATAR));

        verify(imageRepository, never()).save(any());
    }

    // ook testeb of hier werkt dat afbeeldingen in de juiste map worden gezet.

    
}