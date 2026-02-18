package nl.novi.galacticEndgame.mappers;

import nl.novi.galacticEndgame.dtos.user.UserExtendedDTO;
import nl.novi.galacticEndgame.dtos.user.UserRequestDTO;
import nl.novi.galacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.galacticEndgame.entities.ImageEntity;
import nl.novi.galacticEndgame.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper implements DTOMapper<UserResponseDTO, UserRequestDTO, UserEntity> {

    private final ProfileMapper profileMapper;
    private final HuntMapper huntMapper;

    public UserMapper(ProfileMapper profileMapper, HuntMapper huntMapper) {
        this.profileMapper = profileMapper;
        this.huntMapper = huntMapper;
    }

    public UserResponseDTO mapToDto(UserEntity model) {return mapToDto(model, new UserResponseDTO());}

    public <D extends UserResponseDTO> D mapToDto(UserEntity model, D dto) {
        dto.setUserId(model.getUserId());
        dto.setUsername(model.getUsername());
        dto.setUserRole(model.getUserRole());

        ImageEntity avatar = model.getUserAvatar();
        if (avatar != null) {
            dto.setUserAvatarUrl("/uploads/avatars/" + avatar.getStoredName());
        } else {
            dto.setUserAvatarUrl(null);
        }

        dto.setCreatedAt(model.getCreatedAt());
        dto.setHunts(huntMapper.mapToDto(model.getHunts()));

        if (model.getProfile() != null) {
            dto.setProfile(
                    profileMapper.mapToDto(model.getProfile())
            );
        }

        return dto;
    }

    public UserExtendedDTO mapToExtendedDto(UserEntity user) {
        UserExtendedDTO dto = new UserExtendedDTO();
        dto.setKcid(user.getKcid());
        dto.setBlocked(user.isBlocked());
        dto.setBlockedUntil(user.getBlockedUntil());
        dto.setBlockReason(user.getBlockReason());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setUserRole(user.getUserRole());

        ImageEntity avatar = user.getUserAvatar();
        if (avatar != null) {
            dto.setUserAvatarUrl("/uploads/avatars/" + avatar.getStoredName());
        } else {
            dto.setUserAvatarUrl(null);
        }

        return dto;
    }

    @Override
    public List<UserResponseDTO> mapToDto(List<UserEntity> models) {
        List<UserResponseDTO> dtos = new ArrayList<>();
        for (UserEntity model : models) {
            dtos.add(mapToDto(model));
        }
        return dtos;
    }

    @Override
    public UserEntity mapToEntity(UserRequestDTO userModel) {
        UserEntity entity = new UserEntity();
        entity.setUsername(userModel.getUsername());
        entity.setKcid(userModel.getKcid());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUserRole(userModel.getUserRole());
        return entity;
    }


}
