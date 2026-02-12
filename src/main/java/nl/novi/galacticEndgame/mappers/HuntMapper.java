package nl.novi.galacticEndgame.mappers;

import nl.novi.galacticEndgame.dtos.hunt.HuntRequestDTO;
import nl.novi.galacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.galacticEndgame.entities.HuntEntity;
import nl.novi.galacticEndgame.exeptions.IncorrectInputException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HuntMapper implements DTOMapper<HuntResponseDTO, HuntRequestDTO, HuntEntity> {

    private final PokemonMapper pokemonMapper;

    public HuntMapper(PokemonMapper pokemonMapper) {
        this.pokemonMapper = pokemonMapper;
    }

    @Override
    public HuntResponseDTO mapToDto(HuntEntity model) {
        HuntResponseDTO dto = new HuntResponseDTO();
        dto.setId(model.getId());
        dto.setUsedGame(model.getUsedGame());
        dto.setHuntMethod(model.getHuntMethod());
        dto.setEncounters(model.getEncounters());
        dto.setHuntStatus(model.getHuntStatus());
        dto.setUserId(model.getUserEntity().getUserId());

        dto.setCreateDate(model.getCreateDate());
        dto.setFinishDate(model.getFinishDate());
        dto.setEditDate(model.getEditDate());
        dto.setFinishedHunt(model.getFinishedHunt());

        dto.setPokemon(pokemonMapper.mapToDto(model.getPokemon()));

        return dto;
    }

    @Override
    public List<HuntResponseDTO> mapToDto(List<HuntEntity> models) {
        List<HuntResponseDTO> dtos = new ArrayList<>();
        for (HuntEntity model : models) {
            dtos.add(mapToDto(model));
        }
        return dtos;
    }

    @Override
    public HuntEntity mapToEntity(HuntRequestDTO huntModel) {
        throw new IncorrectInputException("Not implemented");
    }

}
