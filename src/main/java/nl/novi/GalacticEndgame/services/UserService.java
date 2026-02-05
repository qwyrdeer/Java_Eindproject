package nl.novi.GalacticEndgame.services;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import nl.novi.GalacticEndgame.dtos.user.UserRequestDTO;
import nl.novi.GalacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.entities.ProfileEntity;
import nl.novi.GalacticEndgame.entities.UserEntity;
import nl.novi.GalacticEndgame.enums.ImageType;
import nl.novi.GalacticEndgame.exeptions.UserNotFoundException;
import nl.novi.GalacticEndgame.mappers.UserMapper;
import nl.novi.GalacticEndgame.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private final ImageService imageService;

    public UserService(UserRepository userRepository, UserMapper userMapper, ImageService imageService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageService = imageService;
    }

    @Transactional
    public UserResponseDTO findUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User with id: " + id + " is not registered yet.");
        }
        return userMapper.mapToDto(userEntity.get());
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
        user.setProfileEntity(profile);

        return null;
    }

    @Transactional
    public UserResponseDTO uploadAvatar(Long userId, MultipartFile file) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User " + userId + " not found");
        }

        UserEntity userEntity = optionalUser.get();

        ImageEntity avatar = imageService.storeImage(file, ImageType.AVATAR);
        userEntity.setUserAvatar(avatar);

        UserEntity saved = userRepository.save(userEntity);
        return userMapper.mapToDto(saved);
    }


    public void deleteUser(Long dexId) {
    }

    //admin?
//    blockUser;

}
