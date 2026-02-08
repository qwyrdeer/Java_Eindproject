package nl.novi.GalacticEndgame.services;

import jakarta.transaction.Transactional;
import nl.novi.GalacticEndgame.dtos.pokemon.PokemonRequestDTO;
import nl.novi.GalacticEndgame.dtos.pokemon.PokemonResponseDTO;
import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.entities.PokemonEntity;
import nl.novi.GalacticEndgame.enums.ImageType;
import nl.novi.GalacticEndgame.exeptions.IncorrectInputException;
import nl.novi.GalacticEndgame.exeptions.PokemonNotFoundException;
import nl.novi.GalacticEndgame.mappers.PokemonMapper;
import nl.novi.GalacticEndgame.repositories.PokemonRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final PokemonMapper pokemonMapper;
    private final ImageService imageService;

    public PokemonService(PokemonRepository pokemonRepository, PokemonMapper pokemonMapper, ImageService imageService) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonMapper = pokemonMapper;
        this.imageService = imageService;
    }

    @Transactional
    public PokemonResponseDTO findPokemonByDexId(Long dexId) {
        Optional<PokemonEntity> pokemonEntity = pokemonRepository.findByDexId(dexId);
        if (pokemonEntity.isEmpty()) {
            throw new PokemonNotFoundException("Pokemon with dex id '# " + dexId + "' is not registered yet.");
        }
        return pokemonMapper.mapToDto(pokemonEntity.get());
    }

    @Transactional
    public List<PokemonResponseDTO> findAllPokemon() {
        return pokemonMapper.mapToDto(pokemonRepository.findAll());
    }

    @Transactional
    public PokemonResponseDTO createPokemon(PokemonRequestDTO pokemonRequestDTO) {
        PokemonEntity pokemonEntity = pokemonMapper.mapToEntity(pokemonRequestDTO);
        pokemonEntity = pokemonRepository.save(pokemonEntity);
        return pokemonMapper.mapToDto(pokemonEntity);
    }


    private PokemonEntity getPokemonEntity(Long dexId) {
        Optional<PokemonEntity> pokemonEntity = pokemonRepository.findByDexId(dexId);
        if (pokemonEntity.isEmpty()) {
            throw new PokemonNotFoundException("Pokemon with dex id: " + dexId + " was not found.");
        }
        return pokemonEntity.get();
    }

    // Admin functie, nog niet in frontend meegenomen waar en hoe dit aangepast kan worden.
    @Transactional
    public PokemonResponseDTO updatePokemon(Long dexId, PokemonRequestDTO input) {
        PokemonEntity existingPokemon = getPokemonEntity(dexId);
        existingPokemon.setName(input.getName());
        existingPokemon.setDexId(input.getDexId());
        existingPokemon.setShinyImg(input.getShinyImg());
        pokemonRepository.save(existingPokemon);
        return pokemonMapper.mapToDto(existingPokemon);
    }

    @Transactional
    public PokemonResponseDTO uploadGif(Long dexId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IncorrectInputException("Shiny GIF is required");}
        Optional<PokemonEntity> optionalPokemon = pokemonRepository.findByDexId(dexId);

        if (optionalPokemon.isEmpty()) {
            throw new PokemonNotFoundException("Pokemon # " + dexId + " not found");
        }

        PokemonEntity pokemon = optionalPokemon.get();
        ImageEntity shinyImg = imageService.storeImage(file, ImageType.PKMN_GIF);

        pokemon.setShinyImg(shinyImg);

        PokemonEntity saved = pokemonRepository.save(pokemon);
        return pokemonMapper.mapToDto(saved);
    }

    @Transactional
    public Resource loadShinyImg(Long dexId) {
        PokemonEntity pokemon = pokemonRepository.findByDexId(dexId)
                .orElseThrow(() -> new PokemonNotFoundException("Image of Pokemon # " + dexId + " not found."));
        ImageEntity shinyImg = pokemon.getShinyImg();

        return imageService.loadAsResource(shinyImg.getUrl());
    }

    @Transactional
    public void deletePokemon(Long dexId) {
        PokemonEntity pokemon = getPokemonEntity(dexId);
        pokemonRepository.delete(pokemon);
    }
}
