package nl.novi.galacticEndgame.mappers;

import nl.novi.galacticEndgame.dtos.profile.ProfileRequestDTO;
import nl.novi.galacticEndgame.dtos.profile.ProfileResponseDTO;
import nl.novi.galacticEndgame.entities.ProfileEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileMapper implements DTOMapper<ProfileResponseDTO, ProfileRequestDTO, ProfileEntity> {

    @Override
    public ProfileResponseDTO mapToDto(ProfileEntity model) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
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
