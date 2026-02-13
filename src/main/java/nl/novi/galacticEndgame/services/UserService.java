package nl.novi.galacticEndgame.services;

import jakarta.transaction.Transactional;
import nl.novi.galacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.galacticEndgame.entities.ImageEntity;
import nl.novi.galacticEndgame.entities.ProfileEntity;
import nl.novi.galacticEndgame.entities.UserEntity;
import nl.novi.galacticEndgame.enums.ImageType;
import nl.novi.galacticEndgame.exeptions.IncorrectInputException;
import nl.novi.galacticEndgame.exeptions.UserNotFoundException;
import nl.novi.galacticEndgame.mappers.UserMapper;
import nl.novi.galacticEndgame.repositories.ProfileRepository;
import nl.novi.galacticEndgame.repositories.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final ProfileRepository profileRepository;

    public UserService(ProfileRepository profileRepository, UserRepository userRepository, UserMapper userMapper, ImageService imageService) {
        this.userMapper = userMapper;
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
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

    public UserResponseDTO findOrCreateUser(Authentication authentication) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuth.getToken();

        String kcid = jwt.getSubject();
        String username = jwt.getClaim("preferred_username");

        UserEntity user = findOrCreateUserEntity(kcid, username);

        return userMapper.mapToDto(user);
    }

    @Transactional
    public UserEntity createUserEntity(String kcid, String username) {
        UserEntity user = new UserEntity();
        user.setKcid(kcid);
        user.setUsername(username);

        ProfileEntity profile = new ProfileEntity();
        profile.setUser(user);

        user.setProfile(profile);

        return userRepository.save(user);
    }

    private UserEntity findOrCreateUserEntity(String kcid, String username) {
        return userRepository.findByKcid(kcid)
                .orElseGet(() -> createUserEntity(kcid, username));
    }

    @Transactional
    public UserResponseDTO uploadAvatar(Authentication authentication, MultipartFile file) {
        UserEntity user = getCurrentUser(authentication);

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

    @Transactional
    public UserEntity getCurrentUser(Authentication authentication) {
        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new UserNotFoundException("Invalid authentication type.");
        }

        Jwt jwt = jwtAuth.getToken();
        String kcid = jwt.getSubject();

        return userRepository.findByKcid(kcid)
                .orElseGet(() -> createUserFromJwt(jwt));
    }

    private UserEntity createUserFromJwt(Jwt jwt) {
        UserEntity newUser = new UserEntity();

        newUser.setKcid(jwt.getSubject());
        newUser.setUsername(jwt.getClaimAsString("preferred_username"));
        newUser.setCreatedAt(LocalDateTime.now());

        UserEntity savedUser = userRepository.save(newUser);

        ProfileEntity profile = new ProfileEntity();
        profile.setUser(savedUser);
        profile.setProfileText("");
        profileRepository.save(profile);

        return savedUser;
    }

    //admin?
//    blockUser;

}
