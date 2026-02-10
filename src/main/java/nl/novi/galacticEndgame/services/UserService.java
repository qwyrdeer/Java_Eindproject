package nl.novi.galacticEndgame.services;

import jakarta.transaction.Transactional;
import nl.novi.galacticEndgame.dtos.user.UserRequestDTO;
import nl.novi.galacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.galacticEndgame.entities.ImageEntity;
import nl.novi.galacticEndgame.entities.ProfileEntity;
import nl.novi.galacticEndgame.entities.UserEntity;
import nl.novi.galacticEndgame.enums.ImageType;
import nl.novi.galacticEndgame.exeptions.IncorrectInputException;
import nl.novi.galacticEndgame.exeptions.UserNotFoundException;
import nl.novi.galacticEndgame.mappers.UserMapper;
import nl.novi.galacticEndgame.repositories.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;

    public UserService(UserRepository userRepository, UserMapper userMapper, ImageService imageService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageService = imageService;
    }

    @Transactional
    public UserResponseDTO findUserByUserId(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " is not registered yet.");
        }
        return userMapper.mapToDto(userEntity.get());
    }

    private UserEntity getUserEntity(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " was not found.");
        }
        return userEntity.get();
    }

    @Transactional
    public UserResponseDTO findUserByUser_UsernameIgnoreCase(String username) {
        Optional<UserEntity> userEntity = userRepository.findUserByUsernameIgnoreCase(username);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User " + username + " is not found");
        }
        return userMapper.mapToDto(userEntity.get());
    }

    @Transactional
    public List<UserResponseDTO> findAllUsers() {
        return userMapper.mapToDto(userRepository.findAll());
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO input) {
        // hele proces in keycloak - hoe doe ik dat met aanmaken profiel
        UserEntity user = userMapper.mapToEntity(input);

        ProfileEntity profile = new ProfileEntity();
        profile.setUser(user);

        user.setProfile(profile);
        UserEntity saved = userRepository.save(user);

        return userMapper.mapToDto(saved);
    }

    @Transactional
    public UserResponseDTO uploadAvatar(Long userId, MultipartFile file) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found " + userId));
        String oldUrl = (user.getUserAvatar() != null) ? user.getUserAvatar().getUrl() : null;

        ImageEntity avatar = imageService.storeImage(file, ImageType.AVATAR);
        user.setUserAvatar(avatar);

        if (oldUrl != null) {
            imageService.deleteByUrl(oldUrl);
        }

        UserEntity saved = userRepository.save(user);
        return userMapper.mapToDto(saved);
    }

    public void deleteUser(Long userId) {
        if (userId == null) {
            throw new IncorrectInputException("UserId cannot be null");
        }
        UserEntity user = getUserEntity(userId);
        userRepository.delete(user);
    }

    @Transactional
    public Resource loadUserAvatar(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User " + userId + " not found."));
        ImageEntity avatar = user.getUserAvatar();

        return imageService.loadAsResource(avatar.getUrl());
    }

    //admin?
//    blockUser;

}
