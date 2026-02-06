package nl.novi.GalacticEndgame.mappers;

import nl.novi.GalacticEndgame.dtos.user.UserExtendedDTO;
import nl.novi.GalacticEndgame.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserExtendedMapper extends UserMapper {

  public UserExtendedMapper(ProfileMapper profileMapper, HuntMapper huntMapper) {
            super(profileMapper, huntMapper);
        }

    @Override
    public UserExtendedDTO mapToDto(UserEntity model) {
        UserExtendedDTO result = (UserExtendedDTO) super.mapToDto(model);
        result.setBlocked(model.isBlocked());
        result.setBlockedUntil(model.getBlockedUntil());
        result.setBlockReason(model.getBlockReason());
        return result;
    }
}