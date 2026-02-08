package nl.novi.GalacticEndgame.mappers;

import nl.novi.GalacticEndgame.dtos.pokemon.PokemonRequestDTO;
import nl.novi.GalacticEndgame.dtos.pokemon.PokemonResponseDTO;
import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.entities.PokemonEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PokemonMapper implements DTOMapper<PokemonResponseDTO, PokemonRequestDTO, PokemonEntity>{

    @Override
    public PokemonResponseDTO mapToDto(PokemonEntity model) {
        PokemonResponseDTO dto = new PokemonResponseDTO();
        dto.setDexId(model.getDexId());
        dto.setName(model.getName());
        dto.setHuntCount(model.getHuntCount());
        dto.setDateFirstHunted(model.getDateFirstHunted());
        dto.setShinyImg("/uploads/pkmn_gifs/" + model.getShinyImg().getStoredName());

        return dto;
    }

    @Override
    public List<PokemonResponseDTO> mapToDto(List<PokemonEntity> models) {
        List<PokemonResponseDTO> dtos = new ArrayList<>();
        for (PokemonEntity model : models) {
            dtos.add(mapToDto(model));
        }
        return dtos;
    }

    @Override
    public PokemonEntity mapToEntity(PokemonRequestDTO pokemonModel) {
        PokemonEntity entity = new PokemonEntity();
        entity.setName(pokemonModel.getName());
        return entity;
    }
}
