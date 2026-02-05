package nl.novi.GalacticEndgame.mappers;

import nl.novi.GalacticEndgame.dtos.profile.ProfileExtendedDTO;
import nl.novi.GalacticEndgame.dtos.profile.ProfileRequestDTO;
import nl.novi.GalacticEndgame.dtos.profile.ProfileResponseDTO;
import nl.novi.GalacticEndgame.dtos.user.UserExtendedDTO;
import nl.novi.GalacticEndgame.entities.ProfileEntity;
import nl.novi.GalacticEndgame.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileMapper implements DTOMapper<ProfileResponseDTO, ProfileRequestDTO, ProfileEntity> {

    @Override
    public ProfileResponseDTO mapToDto(ProfileEntity model) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(model.getId());
        dto.setProfileText(model.getProfileText());
        dto.setTwitchUrl(model.getTwitchUrl());
        dto.setYoutubeUrl(model.getYoutubeUrl());
        dto.setDiscordUrl(model.getDiscordUrl());

        return dto;
    }

    @Override
    public List<ProfileResponseDTO> mapToDto(List<ProfileEntity> models) {
        List<ProfileResponseDTO> dtos = new ArrayList<>();
        for (ProfileEntity model : models) {
            dtos.add(mapToDto(model));
        }
        return dtos;
    }

    @Override
    public ProfileEntity mapToEntity(ProfileRequestDTO profileModel) {
        ProfileEntity entity = new ProfileEntity();
        entity.setProfileText(profileModel.getProfileText());
        entity.setDiscordUrl(profileModel.getDiscordUrl());
        entity.setTwitchUrl(profileModel.getTwitchUrl());
        entity.setYoutubeUrl(profileModel.getYoutubeUrl());

        return entity;
    }
}
