package nl.novi.GalacticEndgame.services;

import nl.novi.GalacticEndgame.dtos.hunt.HuntRequestDTO;
import nl.novi.GalacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.GalacticEndgame.entities.HuntEntity;
import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.entities.PokemonEntity;
import nl.novi.GalacticEndgame.entities.UserEntity;
import nl.novi.GalacticEndgame.enums.HuntStatus;
import nl.novi.GalacticEndgame.exeptions.IncorrectInputException;
import nl.novi.GalacticEndgame.mappers.HuntMapper;
import nl.novi.GalacticEndgame.repositories.HuntRepository;
import nl.novi.GalacticEndgame.repositories.PokemonRepository;
import nl.novi.GalacticEndgame.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    UserRepository userRepository;
    @InjectMocks
    HuntService huntService;

    @Test
    void updateHuntShouldUpdatePreviousCreatedHuntWithNewData() {
        // Arrange
        Long id = 1L;
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

        //Act
        HuntResponseDTO result = huntService.updateHunt(id, input);

        //Assert
        assertEquals(200, existing.getEncounters());
        assertEquals("new", existing.getUsedGame());
        assertEquals("new", existing.getHuntMethod());
        assertEquals(HuntStatus.CURRENT, existing.getHuntStatus());
        verify(huntRepository).save(existing);
        verify(huntMapper).mapToDto(existing);

    }

    @Test
    void updateHuntThrowsWhenNoInputForFinishDate() {
        // Arrange
        Long id = 1L;
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
        existing.setHuntStatus(HuntStatus.CURRENT);
        existing.setEncounters(100L);
        existing.setFinishDate(null);

        HuntRequestDTO input = new HuntRequestDTO();
        input.setUsedGame("new");
        input.setHuntMethod("new");
        input.setEncounters(200L);
        input.setHuntStatus(HuntStatus.FINISHED);
        input.setFinishDate(null);

        when(huntRepository.findById(id)).thenReturn(Optional.of(existing));

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> huntService.updateHunt(id, input));
        verify(huntRepository, never()).save(any());
    }

    @Test
    void createHuntButPokemonIdAndNameAreNotAMatchWithPreviousInputSoThrowAndDoNotSaveHunt() {
        // Arrange
        Long dexId = 175L;
        Long userId = 1L;

        HuntRequestDTO create = new HuntRequestDTO();
        create.setUserId(userId);
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

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(pokemonRepository.findByDexId(dexId)).thenReturn(Optional.of(existing));

        //Act & Assert
        assertThrows(IncorrectInputException.class, () -> huntService.createHunt(create, null));

        verify(huntRepository, never()).save(any(HuntEntity.class));
        verify(pokemonRepository, never()).save(any(PokemonEntity.class));
        verify(huntMapper, never()).mapToDto(any(HuntEntity.class));
    }
}