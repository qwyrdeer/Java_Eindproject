package nl.novi.galacticEndgame.services;

import nl.novi.galacticEndgame.dtos.hunt.HuntRequestDTO;
import nl.novi.galacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.galacticEndgame.dtos.pokemon.PokemonResponseDTO;
import nl.novi.galacticEndgame.entities.HuntEntity;
import nl.novi.galacticEndgame.entities.ImageEntity;
import nl.novi.galacticEndgame.entities.PokemonEntity;
import nl.novi.galacticEndgame.entities.UserEntity;
import nl.novi.galacticEndgame.enums.HuntStatus;
import nl.novi.galacticEndgame.exeptions.HuntNotFoundException;
import nl.novi.galacticEndgame.exeptions.IncorrectInputException;
import nl.novi.galacticEndgame.mappers.HuntMapper;
import nl.novi.galacticEndgame.repositories.HuntRepository;
import nl.novi.galacticEndgame.repositories.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class HuntServiceTest {

    @Mock
    HuntRepository huntRepository;
    @Mock
    HuntMapper huntMapper;
    @Mock
    PokemonRepository pokemonRepository;
    @Mock
    PokemonService pokemonService;
    @Mock
    UserService userService;
    @InjectMocks
    HuntService huntService;

    @Test
    void updateHuntShouldUpdatePreviousCreatedHuntWithNewData() {
        Long id = 1L;
        Authentication authentication = mock(Authentication.class);

        UserEntity user = new UserEntity();
        user.setUserId(1L);

        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setDexId(175L);
        pokemon.setName("Togepi");

        HuntEntity existing = new HuntEntity();
        existing.setId(id);
        existing.setUser(user);
        existing.setPokemon(pokemon);

        existing.setUsedGame("old");
        existing.setHuntMethod("old");
        existing.setHuntStatus(HuntStatus.FUTURE);
        existing.setEncounters(100L);
        existing.setFinishDate(null);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setUsedGame("new");
        input.setHuntMethod("new");
        input.setEncounters(200L);
        input.setHuntStatus(HuntStatus.CURRENT);
        input.setFinishDate(null);

        when(huntRepository.findById(id)).thenReturn(Optional.of(existing));

        HuntResponseDTO expected = new HuntResponseDTO();
        when(huntMapper.mapToDto(existing)).thenReturn(expected);

        HuntResponseDTO result = huntService.updateHunt(id, input, authentication);

        assertEquals(200, existing.getEncounters());
        assertEquals("new", existing.getUsedGame());
        assertEquals("new", existing.getHuntMethod());
        assertEquals(HuntStatus.CURRENT, existing.getHuntStatus());
        verify(huntRepository).save(existing);
        verify(huntMapper).mapToDto(existing);
    }

    @Test
    void updateHuntThrowsWhenHuntDoesNotExist() {
        Long id = 99L;
        Authentication authentication = mock(Authentication.class);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setUsedGame("Scarlet");
        input.setHuntMethod("Masuda");
        input.setEncounters(100L);
        input.setHuntStatus(HuntStatus.CURRENT);
        input.setFinishDate(null);

        when(huntRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(HuntNotFoundException.class, () -> huntService.updateHunt(id, input, authentication));

        verify(huntRepository, never()).save(any());
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void updateHuntThrowsWhenNoInputForFinishDate() {
        Long id = 1L;
        Authentication authentication = mock(Authentication.class);

        UserEntity user = new UserEntity();
        user.setUserId(1L);

        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setDexId(175L);

        HuntEntity existing = new HuntEntity();
        existing.setId(id);
        existing.setUser(user);
        existing.setPokemon(pokemon);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setHuntStatus(HuntStatus.FINISHED);
        input.setFinishDate(null);

        when(huntRepository.findById(id)).thenReturn(Optional.of(existing));

        assertThrows(IncorrectInputException.class, () -> huntService.updateHunt(id, input, authentication));
    }

    @Test
    void createHuntButPokemonIdAndNameAreNotAMatchWithPreviousInputSoThrowAndDoNotSaveHunt() {
        Long dexId = 175L;
        Long userId = 1L;
        Authentication authentication = mock(Authentication.class);

        HuntRequestDTO create = new HuntRequestDTO();
        create.setDexId(dexId);
        create.setName("Pikachu");
        create.setUsedGame("SV");
        create.setHuntMethod("Masuda");
        create.setEncounters(10L);
        create.setHuntStatus(HuntStatus.CURRENT);
        create.setFinishDate(null);

        UserEntity user = new UserEntity();
        user.setUserId(userId);

        ImageEntity existingImage = new ImageEntity();
        existingImage.setUrl("/uploads/pokemon-gifs/e1d1cf51-1576-485b-a1c4-44a33889719b.gif");

        PokemonEntity existing = new PokemonEntity();
        existing.setDexId(175L);
        existing.setName("Togepi");
        existing.setShinyImg(existingImage);
        existing.setHuntCount(10L);

        when(pokemonRepository.findByDexId(dexId)).thenReturn(Optional.of(existing));

        assertThrows(IncorrectInputException.class, () -> huntService.createHunt(create, null, authentication));

        verify(huntRepository, never()).save(any(HuntEntity.class));
        verify(pokemonRepository, never()).save(any(PokemonEntity.class));
        verify(huntMapper, never()).mapToDto(any(HuntEntity.class));
    }

    @Test
    void findHuntByIdThrowsWhenIdIsNull() {
        when(huntRepository.findById(null)).thenReturn(Optional.empty());
        assertThrows(HuntNotFoundException.class, () -> huntService.findHuntById(null));
        verify(huntRepository).findById(null);
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void findHuntByIdReturnsDTOWhenHuntExists() {
        Long id = 1L;
        HuntEntity hunt = new HuntEntity();
        hunt.setId(id);

        HuntResponseDTO dto = new HuntResponseDTO();
        when(huntRepository.findById(id)).thenReturn(Optional.of(hunt));
        when(huntMapper.mapToDto(hunt)).thenReturn(dto);

        HuntResponseDTO result = huntService.findHuntById(id);
        assertSame(dto, result);

        verify(huntRepository).findById(id);
        verify(huntMapper).mapToDto(hunt);
    }

    @Test
    void findAllHuntsReturnsDTO() {
        HuntEntity huntOne = new HuntEntity();
        huntOne.setId(1L);
        HuntEntity huntTwo = new HuntEntity();
        huntTwo.setId(2L);
        List<HuntEntity> hunts = List.of(huntOne, huntTwo);

        HuntResponseDTO dtoOne = new HuntResponseDTO();
        HuntResponseDTO dtoTwo = new HuntResponseDTO();
        List<HuntResponseDTO> dtos = List.of(dtoOne, dtoTwo);

        when(huntRepository.findAll()).thenReturn(hunts);
        when(huntMapper.mapToDto(hunts)).thenReturn(dtos);

        List<HuntResponseDTO> result = huntService.findAllHunts();

        assertEquals(dtos.size(), result.size());
        assertSame(dtos, result);

        verify(huntRepository).findAll();
        verify(huntMapper).mapToDto(hunts);

    }

    @Test
    void findHuntsByUserReturnsDTOSWhenHuntsExist() {
        Long userId = 1L;
        HuntEntity hunt1 = new HuntEntity();
        HuntEntity hunt2 = new HuntEntity();
        List<HuntEntity> hunts = List.of(hunt1, hunt2);

        HuntResponseDTO dto1 = new HuntResponseDTO();
        HuntResponseDTO dto2 = new HuntResponseDTO();
        List<HuntResponseDTO> dtos = List.of(dto1, dto2);

        when(huntRepository.findByUserEntity_UserId(userId)).thenReturn(hunts);
        when(huntMapper.mapToDto(hunts)).thenReturn(dtos);

        List<HuntResponseDTO> result = huntService.findHuntsByUser_UserId(userId);

        assertSame(dtos, result);
        assertEquals(2, result.size());

        verify(huntRepository).findByUserEntity_UserId(userId);
        verify(huntMapper).mapToDto(hunts);
    }

    @Test
    void findHuntsByUserThrowsWhenUserIdIsNull() {
        assertThrows(IncorrectInputException.class, () -> huntService.findHuntsByUser_UserId(null));
        verify(huntRepository, never()).findByUserEntity_UserId(any());
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void findHuntsByUsernameReturnsListWhenUsernameExists() {
        String username = "Misty";
        HuntEntity hunt1 = new HuntEntity();
        HuntEntity hunt2 = new HuntEntity();
        List<HuntEntity> hunts = List.of(hunt1, hunt2);

        HuntResponseDTO dto1 = new HuntResponseDTO();
        HuntResponseDTO dto2 = new HuntResponseDTO();
        List<HuntResponseDTO> dtos = List.of(dto1, dto2);

        when(huntRepository.findByUserEntity_UsernameIgnoreCase(username)).thenReturn(hunts);
        when(huntMapper.mapToDto(hunts)).thenReturn(dtos);

        List<HuntResponseDTO> result = huntService.findHuntsByUser_UsernameIgnoreCase(username);

        assertSame(dtos, result);
        assertEquals(2, result.size());

        verify(huntRepository).findByUserEntity_UsernameIgnoreCase(username);
        verify(huntMapper).mapToDto(hunts);
    }

    @Test
    void findHuntsByUserThrowsWhenNoHuntsFound() {
        Long userId = 1L;
        when(huntRepository.findByUserEntity_UserId(userId)).thenReturn(List.of());
        assertThrows(HuntNotFoundException.class, () -> huntService.findHuntsByUser_UserId(userId));
        verify(huntRepository).findByUserEntity_UserId(userId);
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void findHuntsByUsernameButDidNotSpecifyUsername() {
        assertThrows(IncorrectInputException.class, () -> huntService.findHuntsByUser_UsernameIgnoreCase(null));
        verify(huntRepository, never()).findByUserEntity_UsernameIgnoreCase(any());
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void findHuntsByStatusThrowsWhenStatusIsNull() {
        assertThrows(IncorrectInputException.class, () -> huntService.findHuntsByStatus(null));
        verify(huntRepository, never()).findByHuntStatus(any());
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void findHuntsByStatusReturnsListWhenStatusHasHunts() {
        HuntStatus status = HuntStatus.CURRENT;
        HuntEntity hunt1 = new HuntEntity();
        HuntEntity hunt2 = new HuntEntity();
        List<HuntEntity> hunts = List.of(hunt1, hunt2);

        HuntResponseDTO dto1 = new HuntResponseDTO();
        HuntResponseDTO dto2 = new HuntResponseDTO();
        List<HuntResponseDTO> dtos = List.of(dto1, dto2);

        when(huntRepository.findByHuntStatus(status)).thenReturn(hunts);
        when(huntMapper.mapToDto(hunts)).thenReturn(dtos);

        List<HuntResponseDTO> result = huntService.findHuntsByStatus(status);

        assertSame(dtos, result);
        assertEquals(2, result.size());

        verify(huntRepository).findByHuntStatus(status);
        verify(huntMapper).mapToDto(hunts);
    }

    @Test
    void findHuntsByUserAndStatusThrowsWhenStatusIsNull() {
        assertThrows(IncorrectInputException.class, () -> huntService.findHuntsByUserAndStatus(1L, null));
        verify(huntRepository, never()).findByUserEntity_UserIdAndHuntStatus(any(), any());
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void findHuntsByUserAndStatusThrowsWhenUserIdIsNull() {
        assertThrows(IncorrectInputException.class, () -> huntService.findHuntsByUserAndStatus(null, HuntStatus.CURRENT));
        verify(huntRepository, never()).findByUserEntity_UserIdAndHuntStatus(any(), any());
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void findHuntsByUserAndStatusReturnsMappedList() {
        Long userId = 1L;
        HuntStatus status = HuntStatus.CURRENT;

        List<HuntEntity> hunts = List.of(new HuntEntity(), new HuntEntity());
        List<HuntResponseDTO> dtos = List.of(new HuntResponseDTO(), new HuntResponseDTO());

        when(huntRepository.findByUserEntity_UserIdAndHuntStatus(userId, status)).thenReturn(hunts);
        when(huntMapper.mapToDto(hunts)).thenReturn(dtos);

        List<HuntResponseDTO> result = huntService.findHuntsByUserAndStatus(userId, status);

        assertSame(dtos, result);
        assertEquals(2, result.size());

        verify(huntRepository).findByUserEntity_UserIdAndHuntStatus(userId, status);
        verify(huntMapper).mapToDto(hunts);
    }

    @Test
    void findHuntsOfPokemonByNameThrowsWhenNameIsNull() {
        assertThrows(IncorrectInputException.class, () -> huntService.findHuntsOfPokemonByName(null));
        verify(huntRepository, never()).findByPokemon_NameIgnoreCase(any());
        verify(huntMapper, never()).mapToDto((HuntEntity) any());
    }

    @Test
    void findHuntsOfPokemonByNameReturnsMappedList() {
        String name = "Togepi";

        List<HuntEntity> hunts = List.of(new HuntEntity(), new HuntEntity(), new HuntEntity());
        List<HuntResponseDTO> dtos = List.of(new HuntResponseDTO(), new HuntResponseDTO(), new HuntResponseDTO());

        when(huntRepository.findByPokemon_NameIgnoreCase(name)).thenReturn(hunts);
        when(huntMapper.mapToDto(hunts)).thenReturn(dtos);

        List<HuntResponseDTO> result = huntService.findHuntsOfPokemonByName(name);

        assertSame(dtos, result);
        assertEquals(3, result.size());

        verify(huntRepository).findByPokemon_NameIgnoreCase(name);
        verify(huntMapper).mapToDto(hunts);
    }

    @Test
    void createHuntCreatesHuntWhenUserAndPokemonAreValid() {
        Long userId = 1L;
        Long dexId = 175L;
        Authentication authentication = mock(Authentication.class);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setDexId(dexId);
        input.setName("Togepi");
        input.setUsedGame("SV");
        input.setHuntMethod("Masuda");
        input.setEncounters(50L);
        input.setHuntStatus(HuntStatus.CURRENT);

        UserEntity user = new UserEntity();
        user.setUserId(userId);

        PokemonEntity existingPokemon = new PokemonEntity();
        existingPokemon.setDexId(dexId);
        existingPokemon.setName("Togepi");
        existingPokemon.setHuntCount(3L);

        HuntEntity huntSave = new HuntEntity();
        HuntEntity savedHunt = new HuntEntity();

        HuntResponseDTO responseDTO = new HuntResponseDTO();

        when(pokemonRepository.findByDexId(dexId)).thenReturn(Optional.of(existingPokemon));
        when(huntRepository.save(any(HuntEntity.class))).thenReturn(savedHunt);
        when(huntMapper.mapToDto(savedHunt)).thenReturn(responseDTO);

        HuntResponseDTO result = huntService.createHunt(input, null, authentication);

        assertSame(responseDTO, result);

        verify(pokemonRepository).findByDexId(dexId);
        verify(huntRepository).save(any(HuntEntity.class));
        verify(huntMapper).mapToDto(savedHunt);
    }

    @Test
    void createHuntCreatesNewPokemonWhenPokemonDoesNotExist() {
        Long userId = 1L;
        Long dexId = 1050L;
        Authentication authentication = mock(Authentication.class);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setDexId(dexId);
        input.setName("New");
        input.setUsedGame("SV");
        input.setHuntMethod("Random Encounters");
        input.setEncounters(25L);
        input.setHuntStatus(HuntStatus.CURRENT);

        MultipartFile shinyImg = mock(MultipartFile.class);
        PokemonResponseDTO pokemonResponseDTO = new PokemonResponseDTO();
        when(pokemonService.uploadGif(anyLong(), any(MultipartFile.class))).thenReturn(pokemonResponseDTO);

        UserEntity user = new UserEntity();
        user.setUserId(userId);

        PokemonEntity newPokemon = new PokemonEntity();
        newPokemon.setDexId(dexId);
        newPokemon.setName("New");

        HuntEntity savedHunt = new HuntEntity();
        HuntResponseDTO responseDTO = new HuntResponseDTO();

        when(pokemonRepository.findByDexId(dexId)).thenReturn(Optional.empty());
        when(pokemonRepository.save(any(PokemonEntity.class))).thenReturn(newPokemon);
        when(huntRepository.save(any(HuntEntity.class))).thenReturn(savedHunt);
        when(huntMapper.mapToDto(savedHunt)).thenReturn(responseDTO);

        HuntResponseDTO result = huntService.createHunt(input, shinyImg, authentication);

        assertSame(responseDTO, result);

        verify(pokemonRepository).findByDexId(dexId);
        verify(pokemonRepository).save(any(PokemonEntity.class));
        verify(huntRepository).save(any(HuntEntity.class));
        verify(huntMapper).mapToDto(savedHunt);
    }

    @Test
    void createHuntThrowsWhenCreatingNewPokemonWithoutShinyImg() {
        HuntRequestDTO input = new HuntRequestDTO();
        input.setDexId(25L);
        input.setName("Pikachu");

        MultipartFile shinyImg = null;

        assertThrows(IncorrectInputException.class, () -> huntService.createNewPokemon(input, shinyImg));
        verify(pokemonRepository, never()).save(any());
        verify(pokemonService, never()).uploadGif(anyLong(), any());
    }

    @Test
    void createHuntThrowsWhenCreatingNewPokemonWithoutImg() {
        HuntRequestDTO input = new HuntRequestDTO();
        input.setDexId(25L);
        input.setName("Pikachu");

        MultipartFile shinyImg = mock(MultipartFile.class);
        when(shinyImg.isEmpty()).thenReturn(true);

        assertThrows(IncorrectInputException.class, () -> huntService.createNewPokemon(input, shinyImg));

        verify(pokemonRepository, never()).save(any());
        verify(pokemonService, never()).uploadGif(anyLong(), any());
    }

    @Test
    void createHuntExistingPokemonAndShinyGifThrows() {
        Authentication authentication = mock(Authentication.class);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setDexId(25L);
        input.setName("Pikachu");

        UserEntity user = new UserEntity();
        user.setUserId(1L);

        PokemonEntity existingPokemon = new PokemonEntity();
        existingPokemon.setDexId(25L);
        existingPokemon.setName("Pikachu");
        existingPokemon.setHuntCount(3L);

        MultipartFile shinyImg = mock(MultipartFile.class);
        when(shinyImg.isEmpty()).thenReturn(false);

        when(pokemonRepository.findByDexId(25L)).thenReturn(Optional.of(existingPokemon));

        IncorrectInputException exception = assertThrows(IncorrectInputException.class, () -> huntService.createHunt(input, shinyImg, authentication));

        assertEquals("GIF can only be uploaded at the first hunt of a Pokémon", exception.getMessage());

        verify(huntRepository, never()).save(any());
        verify(pokemonRepository, never()).save(any());
        verify(pokemonService, never()).uploadGif(anyLong(), any());
    }

    @Test
    void updateHuntWithoutInputThrows() {
        Long id = 17L;
        Authentication authentication = mock(Authentication.class);
        assertThrows(IncorrectInputException.class, () -> huntService.updateHunt(id, null, authentication));
        verify(huntRepository, never()).save(any(HuntEntity.class));
    }

    @Test
    void updateExistingPokemonSetsFirstHuntedDateWhenNull() {
        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setDexId(175L);
        pokemon.setName("Togepi");
        pokemon.setHuntCount(5L);
        pokemon.setDateFirstHunted(null);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setDexId(175L);
        input.setName("Togepi");

        PokemonEntity result = huntService.updateExistingPokemon(pokemon, input);

        assertEquals(6L, result.getHuntCount());
        assertNotNull(result.getDateFirstHunted());
    }

    @Test
    void updateHuntWithNoFinishInputThrows() {
        Long id = 17L;
        Authentication authentication = mock(Authentication.class);
        HuntEntity hunt = new HuntEntity();
        hunt.setId(id);
        hunt.setHuntStatus(HuntStatus.CURRENT);

        HuntRequestDTO dto = new HuntRequestDTO();
        dto.setUsedGame("Scarlet");
        dto.setHuntMethod("Masuda");
        dto.setEncounters(100L);
        dto.setHuntStatus(HuntStatus.FINISHED);
        dto.setFinishDate(null);

        when(huntRepository.findById(id)).thenReturn(Optional.of(hunt));
        assertThrows(IncorrectInputException.class, () -> huntService.updateHunt(id, dto, authentication));
        verify(huntRepository, never()).save(any());
    }

    @Test
    void updateExistingPokemonDoesNotOverwriteFirstHuntedDate() {
        LocalDateTime originalDate = LocalDateTime.now().minusDays(5);

        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setDexId(175L);
        pokemon.setName("Togepi");
        pokemon.setHuntCount(1L);
        pokemon.setDateFirstHunted(originalDate);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setDexId(175L);
        input.setName("Togepi");

        PokemonEntity result = huntService.updateExistingPokemon(pokemon, input);

        assertEquals(originalDate, result.getDateFirstHunted());
        assertEquals(2L, result.getHuntCount());
    }

    @Test
    void updateHuntUpdatesHuntAndReturnsDTO() {
        Long id = 1L;
        Authentication authentication = mock(Authentication.class);

        HuntEntity existing = new HuntEntity();
        existing.setId(id);
        existing.setUsedGame("oldGame");
        existing.setHuntMethod("oldMethod");
        existing.setEncounters(50L);
        existing.setHuntStatus(HuntStatus.FUTURE);
        existing.setFinishDate(null);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setUsedGame("Scarlet");
        input.setHuntMethod("Masuda");
        input.setEncounters(200L);
        input.setHuntStatus(HuntStatus.CURRENT);
        input.setFinishDate(null);

        HuntResponseDTO expectedDto = new HuntResponseDTO();

        when(huntRepository.findById(id)).thenReturn(Optional.of(existing));
        when(huntMapper.mapToDto(existing)).thenReturn(expectedDto);

        HuntResponseDTO result = huntService.updateHunt(id, input, authentication);

        assertSame(expectedDto, result);
        assertEquals("Scarlet", existing.getUsedGame());
        assertEquals("Masuda", existing.getHuntMethod());
        assertEquals(200L, existing.getEncounters());
        assertEquals(HuntStatus.CURRENT, existing.getHuntStatus());

        verify(huntRepository).save(existing);
        verify(huntMapper).mapToDto(existing);
    }

    @Test
    void deleteHuntThatDoesNotExist() {
        Long id = 100L;
        when(huntRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(HuntNotFoundException.class, () -> huntService.deleteHunt(id));
        verify(huntRepository, never()).delete(any());
    }

    @Test
    void deleteHuntThrowsWhenIdIsNull() {
        assertThrows(IncorrectInputException.class, () -> huntService.deleteHunt(null));
        verify(huntRepository, never()).findById(any());
        verify(huntRepository, never()).delete(any());
    }

    @Test
    void deleteHuntSuccessfully() {
        Long id = 1L;

        HuntEntity hunt = new HuntEntity();
        hunt.setId(id);

        when(huntRepository.findById(id)).thenReturn(Optional.of(hunt));
        huntService.deleteHunt(id);
        verify(huntRepository).delete(hunt);
    }
}