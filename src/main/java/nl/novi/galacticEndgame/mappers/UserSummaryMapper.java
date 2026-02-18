package nl.novi.galacticEndgame.mappers;

import nl.novi.galacticEndgame.dtos.user.UserSummaryDTO;
import nl.novi.galacticEndgame.entities.ImageEntity;
import nl.novi.galacticEndgame.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserSummaryMapper {
    public UserSummaryDTO mapToDto(UserEntity user) {

        UserSummaryDTO dto = new UserSummaryDTO();

        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());

        ImageEntity avatar = user.getUserAvatar();
        if (avatar != null) {
            dto.setUserAvatarUrl("/uploads/avatars/" + avatar.getStoredName());
        } else {
            dto.setUserAvatarUrl(null);
        }

        dto.setUserRole(user.getUserRole());

        return dto;
    }
}