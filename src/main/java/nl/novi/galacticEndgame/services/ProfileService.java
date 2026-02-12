package nl.novi.galacticEndgame.services;

import jakarta.transaction.Transactional;
import nl.novi.galacticEndgame.dtos.profile.ProfileRequestDTO;
import nl.novi.galacticEndgame.dtos.profile.ProfileResponseDTO;
import nl.novi.galacticEndgame.entities.ProfileEntity;
import nl.novi.galacticEndgame.exeptions.ProfileNotFoundException;
import nl.novi.galacticEndgame.mappers.ProfileMapper;
import nl.novi.galacticEndgame.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    @Transactional
    public ProfileResponseDTO findProfileByUser_UserId(Long userId) {
        Optional<ProfileEntity> profileEntity = profileRepository.findByUser_UserId(userId);
        if (profileEntity.isEmpty()) {
            throw new ProfileNotFoundException("Profile of user " + userId + " is not found");
        }
        return profileMapper.mapToDto(profileEntity.get());
    }

    @Transactional
    public ProfileResponseDTO findProfileByUser_UsernameIgnoreCase(String username) {
        Optional<ProfileEntity> profileEntity = profileRepository.findByUser_UsernameIgnoreCase(username);
        if (profileEntity.isEmpty()) {
            throw new ProfileNotFoundException("Profile of user " + username + " is not found");
        }
        return profileMapper.mapToDto(profileEntity.get());
    }

    @Transactional
    public ProfileResponseDTO updateProfileByUserId(Long userId, ProfileRequestDTO input) {
        Optional<ProfileEntity> optional = profileRepository.findByUser_UserId(userId);
        if (optional.isEmpty()) {
            throw new ProfileNotFoundException("Profile of user with id " + userId + " is not found");
        }
        ProfileEntity profileEntity = optional.get();

        profileEntity.setProfileText(input.getProfileText());
        profileEntity.setDiscordUrl(input.getDiscordUrl());
        profileEntity.setTwitchUrl(input.getTwitchUrl());
        profileEntity.setYoutubeUrl(input.getYoutubeUrl());

        ProfileEntity saved = profileRepository.save(profileEntity);
        return profileMapper.mapToDto(saved);
    }
}
