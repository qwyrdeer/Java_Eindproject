package nl.novi.GalacticEndgame.mappers;

import nl.novi.GalacticEndgame.dtos.user.UserRequestDTO;
import nl.novi.GalacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.GalacticEndgame.entities.UserEntity;
import nl.novi.GalacticEndgame.enums.UserRole;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper implements DTOMapper<UserResponseDTO, UserRequestDTO, UserEntity> {

    @Override
    public UserResponseDTO mapToDto(UserEntity model) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(model.getUserId());
        dto.setUsername(model.getUsername());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setLastLogin(model.getLastLogin());
        dto.setBlocked(model.isBlocked());
        dto.setBlockedUntil(model.getBlockedUntil());
        dto.setBlockReason(model.getBlockReason());
        dto.setUserAvatar(model.getUserAvatar());
        dto.setUserRole(model.getUserRole());
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
        entity.setUserRole(UserRole.USER);
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }


}
