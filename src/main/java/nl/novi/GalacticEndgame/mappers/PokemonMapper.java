package nl.novi.GalacticEndgame.mappers;

import nl.novi.GalacticEndgame.dtos.pokemon.PokemonRequestDTO;
import nl.novi.GalacticEndgame.dtos.pokemon.PokemonResponseDTO;
import nl.novi.GalacticEndgame.entities.PokemonEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PokemonMapper implements DTOMapper<PokemonRequestDTO, PokemonResponseDTO, PokemonEntity>{

    @Override
    public PokemonRequestDTO mapToDto(PokemonEntity model) {
        PokemonRequestDTO dto = new PokemonRequestDTO();
        dto.setDexId(model.getDexId());
        dto.setName(model.getName());
        dto.setHuntCount(model.getHuntCount());
        dto.setDateFirstHunted(model.getDateFirstHunted());
        // gif toevoegen

        return dto;
    }

    @Override
    public List<PokemonRequestDTO> mapToDto(List<PokemonEntity> models) {
        return List.of();
    }

    @Override
    public PokemonEntity mapToEntity(PokemonResponseDTO huntModel) {
        return null;
    }
}
