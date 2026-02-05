package nl.novi.GalacticEndgame.services;

import jakarta.transaction.Transactional;
import nl.novi.GalacticEndgame.dtos.hunt.HuntRequestDTO;
import nl.novi.GalacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.GalacticEndgame.entities.HuntEntity;
import nl.novi.GalacticEndgame.entities.PokemonEntity;
import nl.novi.GalacticEndgame.enums.HuntStatus;
import nl.novi.GalacticEndgame.exeptions.HuntNotFoundException;
import nl.novi.GalacticEndgame.exeptions.IncorrectInputException;
import nl.novi.GalacticEndgame.mappers.HuntMapper;
import nl.novi.GalacticEndgame.repositories.HuntRepository;
import nl.novi.GalacticEndgame.repositories.PokemonRepository;
import nl.novi.GalacticEndgame.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HuntService {

    private final HuntMapper huntMapper;
    private final HuntRepository huntRepository;
    private final UserRepository userRepository;
    private final PokemonRepository pokemonRepository;

    public HuntService(HuntMapper huntMapper, HuntRepository huntRepository, UserRepository userRepository, PokemonRepository pokemonRepository) {
        this.huntMapper = huntMapper;
        this.huntRepository = huntRepository;
        this.userRepository = userRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Transactional
    public HuntResponseDTO findHuntById(Long id) {
        Optional<HuntEntity> huntEntity = huntRepository.findById(id);
        if (huntEntity.isEmpty()) {
            throw new HuntNotFoundException("Hunt with id " +id + " is not found.");
        }
        return huntMapper.mapToDto(huntEntity.get());
    }

    public List<HuntResponseDTO> findAllHunts() {
        return huntMapper.mapToDto(huntRepository.findAll());
    }

    public List<HuntResponseDTO> findHuntsByUser_UserId(Long userId) {
        if (userId == null) {
            throw new IncorrectInputException("userId cannot be null");
        }
        List<HuntEntity> hunts = huntRepository.findByUser_UserId(userId);
        if (hunts.isEmpty()) {
            throw new HuntNotFoundException("No hunts found for userId " + userId);
        }
        return huntMapper.mapToDto(hunts);
    }

    @Transactional
    public List<HuntResponseDTO> findHuntsByUser_UsernameIgnoreCase(String username) {
        if (username == null){
            throw new IncorrectInputException("Username cannot be null");
        }
        List<HuntEntity> hunts = huntRepository.findByUser_UsernameIgnoreCase(username);
        return huntMapper.mapToDto(hunts);
    }

    @Transactional
    public List<HuntResponseDTO> findHuntsByStatus(HuntStatus status) {
        if (status == null) {
            throw new IncorrectInputException("Status cannot be null");
        }
        List<HuntEntity> hunts = huntRepository.findByHuntStatus(status);
        return huntMapper.mapToDto(hunts);
    }

    @Transactional
    public List<HuntResponseDTO> findHuntsByUserAndStatus(Long userId, HuntStatus status) {
        if (status == null) {
            throw new IncorrectInputException("Status cannot be null");
        }
        if (userId == null){
            throw new IncorrectInputException("UserId cannot be null");
        }
        List<HuntEntity> hunts = huntRepository.findByUser_UserIdAndHuntStatus(userId, status);
        return huntMapper.mapToDto(hunts);
    }

    @Transactional
    public List<HuntResponseDTO> findHuntsOfPokemonByName(String name) {
        if (name == null){
            throw new IncorrectInputException("Name of Pokemon cannot be null");
        }
        List<HuntEntity> hunts = huntRepository.findByPokemonIgnoreCase(name);
        return huntMapper.mapToDto(hunts);
    }

    private HuntEntity getHuntEntity(Long id) {
        Optional<HuntEntity> huntEntity = huntRepository.findById(id);
        if (huntEntity.isEmpty()) {
            throw new HuntNotFoundException("Hunt with id: " + id + " was not found.");
        }
        return huntEntity.get();
    }

    @Transactional
    public HuntResponseDTO createHunt(HuntRequestDTO huntRequestDTO) {

        // werkt zo niet, misschien met authenticatie later?
//        UserEntity user = userRepository.findById(huntRequestDTO.getUserId());
//        if (user.isEmpty()) {
//            throw new UserNotFoundException("User not found");
//        }

        PokemonEntity pokemon = pokemonRepository.findByDexId(huntRequestDTO.getPokemonDexId())
                .map(existing -> {
                    if (!existing.getName().equalsIgnoreCase(huntRequestDTO.getPokemonName())) {
                        throw new IncorrectInputException( "DexId " + huntRequestDTO.getPokemonDexId() + " already exists with name '" +
                                existing.getName() + "', not '" + huntRequestDTO.getPokemonName() + "'" );
                    }
                    existing.setHuntCount(existing.getHuntCount() + 1);
                    if (existing.getDateFirstHunted() == null) {
                        existing.setDateFirstHunted(LocalDateTime.now());
                    }
                    return existing;
                })
                .orElseGet(() -> {
                    PokemonEntity created = new PokemonEntity();
                    created.setDexId(huntRequestDTO.getPokemonDexId());
                    created.setName(huntRequestDTO.getPokemonName());

                    created.setHuntCount(1L);
                    created.setDateFirstHunted(LocalDateTime.now());

                    return pokemonRepository.save(created);
                });

        HuntEntity hunt = new HuntEntity();
        hunt.setUser(hunt.getUserEntity());
        hunt.setPokemon(pokemon);

        hunt.setUsedGame(huntRequestDTO.getUsedGame());
        hunt.setHuntMethod(huntRequestDTO.getHuntMethod());
        hunt.setEncounters(huntRequestDTO.getEncounters());
//        hunt.setHuntStatus(huntRequestDTO.setHuntStatus());

        HuntEntity saved = huntRepository.save(hunt);
        return huntMapper.mapToDto(saved);
    }

    @Transactional
    public HuntResponseDTO updateHunt(Long id, HuntRequestDTO huntInput) {
        if (huntInput == null) {
            throw new IncorrectInputException("Hunt input cannot be null");
        }
        HuntEntity existingHuntEntity = getHuntEntity(id);

        existingHuntEntity.setUsedGame(huntInput.getUsedGame());
        existingHuntEntity.setHuntMethod(huntInput.getHuntMethod());
        existingHuntEntity.setEncounters(huntInput.getEncounters());

    // Status - automatische afronding (data) wanneer omgezet naar FINISHED?]

        huntRepository.save(existingHuntEntity);
        return huntMapper.mapToDto(existingHuntEntity);
    }

    @Transactional
    public void deleteHunt(Long id) {
        HuntEntity pokemon = getHuntEntity(id);
        huntRepository.delete(pokemon);
    }
}
