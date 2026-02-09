package nl.novi.GalacticEndgame.services;

import nl.novi.GalacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.GalacticEndgame.entities.HuntEntity;
import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.entities.UserEntity;
import nl.novi.GalacticEndgame.enums.ImageType;
import nl.novi.GalacticEndgame.exeptions.IncorrectInputException;
import nl.novi.GalacticEndgame.exeptions.StoringException;
import nl.novi.GalacticEndgame.mappers.UserMapper;
import nl.novi.GalacticEndgame.repositories.ImageRepository;
import nl.novi.GalacticEndgame.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    ImageService imageService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    void uploadAvatarReplacesOldAvatar() {
        Long userId = 1L;

        ImageEntity existingAvatar = new ImageEntity();
        existingAvatar.setId(10L);
        existingAvatar.setUrl("/uploads/avatars/old.png");

        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setUserAvatar(existingAvatar);

        ImageEntity newAvatar = new ImageEntity();
        newAvatar.setId(20L);
        newAvatar.setUrl("/uploads/avatars/new.png");

        MultipartFile file = mock(MultipartFile.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(imageService.storeImage(file, ImageType.AVATAR)).thenReturn(newAvatar);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapToDto(any(UserEntity.class))).thenReturn(new UserResponseDTO());

        userService.uploadAvatar(userId, file);
        assertEquals(newAvatar, user.getUserAvatar());
        verify(imageService).deleteByUrl("/uploads/avatars/old.png");

    }

}