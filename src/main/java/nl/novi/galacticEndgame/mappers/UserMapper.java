package nl.novi.galacticEndgame.mappers;

import nl.novi.galacticEndgame.dtos.user.UserRequestDTO;
import nl.novi.galacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.galacticEndgame.entities.UserEntity;
import nl.novi.galacticEndgame.enums.UserRole;
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

    public <D extends UserResponseDTO> D mapToDto(UserEntity model, D target) {
        target.setUserId(model.getUserId());
        target.setUsername(model.getUsername());
        target.setUserAvatarUrl("/uploads/avatars/" + target.getUserId());
        target.setUserRole(model.getUserRole());
        target.setCreatedAt(model.getCreatedAt());
        target.setHunts(huntMapper.mapToDto(model.getHunts()));

        if (model.getProfile() != null) {
            target.setProfile(
                    profileMapper.mapToDto(model.getProfile())
            );
        }

        return target;
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
        entity.setUserRole(UserRole.USER);
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }


}
