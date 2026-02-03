package nl.novi.GalacticEndgame.services;

import jakarta.transaction.Transactional;
import nl.novi.GalacticEndgame.dtos.profile.ProfileResponseDTO;
import nl.novi.GalacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.GalacticEndgame.entities.ProfileEntity;
import nl.novi.GalacticEndgame.entities.UserEntity;
import nl.novi.GalacticEndgame.exeptions.ProfileNotFoundException;
import nl.novi.GalacticEndgame.exeptions.UserNotFoundException;
import nl.novi.GalacticEndgame.mappers.ProfileMapper;
import nl.novi.GalacticEndgame.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

public class ProfileService {

    private ProfileRepository profileRepository;
    private ProfileMapper profileMapper;

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

    updateProfile;

//    createProfile;
//    deleteProfile;
}
