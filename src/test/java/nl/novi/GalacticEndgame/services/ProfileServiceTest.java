package nl.novi.GalacticEndgame.services;

import nl.novi.GalacticEndgame.dtos.profile.ProfileRequestDTO;
import nl.novi.GalacticEndgame.dtos.profile.ProfileResponseDTO;
import nl.novi.GalacticEndgame.entities.ProfileEntity;
import nl.novi.GalacticEndgame.exeptions.ProfileNotFoundException;
import nl.novi.GalacticEndgame.mappers.ProfileMapper;
import nl.novi.GalacticEndgame.repositories.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    ProfileRepository profileRepository;
    @Mock
    ProfileMapper profileMapper;

    @InjectMocks
    ProfileService profileService;

    @Test
    void updateProfileByUserIdWithNewData() {
        // Arrange
        Long userId = 1L;

        ProfileEntity existing = new ProfileEntity();
        existing.setProfileText("Old text");
        existing.setDiscordUrl(null);
        existing.setTwitchUrl("https://www.twitch.tv/ludwig");
        existing.setYoutubeUrl(null);

        ProfileRequestDTO input = new ProfileRequestDTO();
        input.setProfileText("New text");
        input.setDiscordUrl(null);
        input.setTwitchUrl("https://www.twitch.tv/ludwig");
        input.setYoutubeUrl("https://www.youtube.com/channel/UCrPseYLGpNygVi34QpGNqpA");

        when(profileRepository.save(existing)).thenReturn(existing);
        when(profileRepository.findByUser_UserId(userId)).thenReturn(Optional.of(existing));

        ProfileResponseDTO expected = new ProfileResponseDTO();
        when(profileMapper.mapToDto(existing)).thenReturn(expected);

        //Act
        ProfileResponseDTO result = profileService.updateProfileByUserId(userId, input);
        assertSame(expected, result);

        //Assert
        assertEquals("New text", existing.getProfileText());
        assertNull(existing.getDiscordUrl());
        assertEquals("https://www.twitch.tv/ludwig", existing.getTwitchUrl());
        assertEquals("https://www.youtube.com/channel/UCrPseYLGpNygVi34QpGNqpA", existing.getYoutubeUrl());

        verify(profileRepository).save(existing);
        verify(profileMapper).mapToDto(existing);
        verify(profileRepository).findByUser_UserId(userId);
    }

    @Test
    void updateProfileByUserIdWhenProfileNotFoundThrow() {
        // Arrange
        Long userId = 1L;
        ProfileRequestDTO input = new ProfileRequestDTO();

        when(profileRepository.findByUser_UserId(userId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ProfileNotFoundException.class, () -> profileService.updateProfileByUserId(userId, input));

        verify(profileRepository).findByUser_UserId(userId);
        verify(profileRepository, never()).save(any());
        verify(profileMapper, never()).mapToDto(any(ProfileEntity.class));
    }

    @Test
    void findProfileByUser_UserId() {
    }

    @Test
    void findProfileByUser_UsernameIgnoreCase() {
    }

    @Test
    void updateProfileByUserId() {
    }
}