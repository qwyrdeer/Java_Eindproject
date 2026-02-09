package nl.novi.GalacticEndgame.services;

import jakarta.transaction.Transactional;
import nl.novi.GalacticEndgame.dtos.hunt.HuntRequestDTO;
import nl.novi.GalacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.GalacticEndgame.entities.HuntEntity;
import nl.novi.GalacticEndgame.entities.PokemonEntity;
import nl.novi.GalacticEndgame.entities.UserEntity;
import nl.novi.GalacticEndgame.enums.HuntStatus;
import nl.novi.GalacticEndgame.exeptions.HuntNotFoundException;
import nl.novi.GalacticEndgame.exeptions.IncorrectInputException;
import nl.novi.GalacticEndgame.exeptions.UserNotFoundException;
import nl.novi.GalacticEndgame.mappers.HuntMapper;
import nl.novi.GalacticEndgame.repositories.HuntRepository;
import nl.novi.GalacticEndgame.repositories.PokemonRepository;
import nl.novi.GalacticEndgame.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HuntService {

    private final HuntMapper huntMapper;
    private final HuntRepository huntRepository;
    private final UserRepository userRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonService pokemonService;

    public HuntService(HuntMapper huntMapper, HuntRepository huntRepository, UserRepository userRepository, PokemonRepository pokemonRepository, PokemonService pokemonService) {
        this.huntMapper = huntMapper;
        this.huntRepository = huntRepository;
        this.userRepository = userRepository;
        this.pokemonRepository = pokemonRepository;
        this.pokemonService = pokemonService;
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
        List<HuntEntity> hunts = huntRepository.findByUserEntity_UserId(userId);
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
        List<HuntEntity> hunts = huntRepository.findByUserEntity_UsernameIgnoreCase(username);
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
        List<HuntEntity> hunts = huntRepository.findByUserEntity_UserIdAndHuntStatus(userId, status);
        return huntMapper.mapToDto(hunts);
    }

    @Transactional
    public List<HuntResponseDTO> findHuntsOfPokemonByName(String name) {
        if (name == null){
            throw new IncorrectInputException("Name of Pokemon cannot be null");
        }
        List<HuntEntity> hunts = huntRepository.findByPokemon_NameIgnoreCase(name);
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
    public HuntResponseDTO createHunt(HuntRequestDTO input, MultipartFile shinyImg) {
        UserEntity user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        PokemonEntity pokemon = pokemonRepository.findByDexId(input.getDexId())
                .map(existing -> updateExistingPokemon(existing, input))
                .orElseGet(() -> createNewPokemon(input, shinyImg));

        HuntEntity hunt = createHuntEntity(user, pokemon, input);
        HuntEntity saved = huntRepository.save(hunt);
        return huntMapper.mapToDto(saved);
    }

    private PokemonEntity updateExistingPokemon(PokemonEntity pokemon, HuntRequestDTO input) {
        if (!pokemon.getName().equalsIgnoreCase(input.getName())) {
            throw new IncorrectInputException(
                    "DexId " + input.getDexId() + " already exists with name '" +
                            pokemon.getName() + "', not '" + input.getName() + "'"
            );
        }
        pokemon.setHuntCount(pokemon.getHuntCount() + 1);
        if (pokemon.getDateFirstHunted() == null) {
            pokemon.setDateFirstHunted(LocalDateTime.now());
        }
        return pokemon;
    }

    private PokemonEntity createNewPokemon(HuntRequestDTO input, MultipartFile shinyImg) {
        if (isFileEmpty(shinyImg)) {
            throw new IncorrectInputException("Adding a shiny GIF is required for new Pokémon");
        }

        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setDexId(input.getDexId());
        pokemon.setName(input.getName());
        pokemon.setHuntCount(1L);
        pokemon.setDateFirstHunted(LocalDateTime.now());

        PokemonEntity saved = pokemonRepository.save(pokemon);
        pokemonService.uploadGif(saved.getDexId(), shinyImg);
        return saved;
    }

    private HuntEntity createHuntEntity(UserEntity user, PokemonEntity pokemon, HuntRequestDTO input) {
        HuntEntity hunt = new HuntEntity();
        hunt.setUserEntity(user);
        hunt.setPokemon(pokemon);
        hunt.setUsedGame(input.getUsedGame());
        hunt.setHuntMethod(input.getHuntMethod());
        hunt.setEncounters(input.getEncounters());
        hunt.setHuntStatus(input.getHuntStatus());
        hunt.setCreateDate(LocalDateTime.now());
        hunt.setEditDate(LocalDateTime.now());
        return hunt;
    }

    private boolean isFileEmpty(MultipartFile file) {
        return file == null || file.isEmpty();
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
        existingHuntEntity.changeStatus(huntInput.getHuntStatus(), huntInput.getFinishDate());

        if (huntInput.getHuntStatus() == HuntStatus.FINISHED && huntInput.getFinishDate() == null) {
            throw new IncorrectInputException(
                    "Finish date is required when hunt status is FINISHED"
            );
        }

        huntRepository.save(existingHuntEntity);
        return huntMapper.mapToDto(existingHuntEntity);
    }

    @Transactional
    public void deleteHunt(Long id) {
        HuntEntity pokemon = getHuntEntity(id);
        huntRepository.delete(pokemon);
    }
}
