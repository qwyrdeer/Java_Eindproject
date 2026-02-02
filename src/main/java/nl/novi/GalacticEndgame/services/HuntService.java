package nl.novi.GalacticEndgame.services;

import jakarta.transaction.Transactional;
import nl.novi.GalacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.GalacticEndgame.entities.HuntEntity;
import nl.novi.GalacticEndgame.exeptions.HuntNotFoundException;
import nl.novi.GalacticEndgame.mappers.HuntMapper;
import nl.novi.GalacticEndgame.repositories.HuntRepository;

import java.util.Optional;

public class HuntService {

    private final HuntMapper huntMapper;
    private final HuntRepository huntRepository;

    public HuntService(HuntMapper huntMapper, HuntRepository huntRepository) {
        this.huntMapper = huntMapper;
        this.huntRepository = huntRepository;
    }

    @Transactional
    public HuntResponseDTO findHuntById(Long id) {
        Optional<HuntEntity> huntEntity = huntRepository.findById(id);
        if (huntEntity.isEmpty()) {
            throw new HuntNotFoundException("Hunt with id " +id + " is not found.");
        }
        return huntMapper.mapToDto(huntEntity.get());
    }

    findAllHunts;
    findHuntsByUser_UserId;
    findHuntsByStatus;
    findHuntsByUserAndStatus;
    findHuntsOfPokemonByName;

    private HuntEntity getHuntEntity(Long dexId) {
        Optional<HuntEntity> huntEntity = huntRepository.findById(id);
        if (huntEntity.isEmpty()) {
            throw new HuntNotFoundException("Hunt with id: " + id + " was not found.");
        }
        return huntEntity.get();
    }

    createHunt;
    updateHunt;

    @Transactional
    public void deleteHunt(Long id) {
        HuntEntity pokemon = getHuntEntity(id);
        huntRepository.delete(pokemon);
    }
}
