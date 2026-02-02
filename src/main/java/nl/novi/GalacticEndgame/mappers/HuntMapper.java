package nl.novi.GalacticEndgame.mappers;

import nl.novi.GalacticEndgame.dtos.hunt.HuntRequestDTO;
import nl.novi.GalacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.GalacticEndgame.entities.HuntEntity;

import java.util.List;

public class HuntMapper implements DTOMapper<HuntRequestDTO, HuntResponseDTO, HuntEntity> {
    private final PokemonMapper pokemonMapper;

    public HuntMapper(PokemonMapper pokemonMapper) {
        this.pokemonMapper = pokemonMapper;
    }


    @Override
    public HuntRequestDTO mapToDto(HuntEntity model) {
        return null;
    }

    @Override
    public List<HuntRequestDTO> mapToDto(List<HuntEntity> models) {
        return List.of();
    }

    @Override
    public HuntEntity mapToEntity(HuntResponseDTO huntModel) {
        return null;
    }
}
