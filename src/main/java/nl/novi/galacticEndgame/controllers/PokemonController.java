package nl.novi.galacticEndgame.controllers;

import jakarta.validation.Valid;
import nl.novi.galacticEndgame.dtos.pokemon.PokemonRequestDTO;
import nl.novi.galacticEndgame.dtos.pokemon.PokemonResponseDTO;
import nl.novi.galacticEndgame.helpers.UrlHelper;
import nl.novi.galacticEndgame.services.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;
    private final UrlHelper urlHelper;

    public PokemonController(PokemonService pokemonService, UrlHelper urlHelper) {
        this.pokemonService = pokemonService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<PokemonResponseDTO>> getAllPokemon() {
        List<PokemonResponseDTO> pokemon = pokemonService.findAllPokemon();
        return new ResponseEntity<>(pokemon, HttpStatus.OK);
    }

    @GetMapping("/{dexId}")
    public ResponseEntity<PokemonResponseDTO> getPokemonByDexId(@PathVariable Long dexId) {
        PokemonResponseDTO pokemon = pokemonService.findPokemonByDexId(dexId);
        return new ResponseEntity<>(pokemon, HttpStatus.OK);
    }

    // alleen met aanmaken hunt?
    @PostMapping
    public ResponseEntity<PokemonResponseDTO> createPokemon (@Valid @RequestBody PokemonRequestDTO pokemonModel) {
        PokemonResponseDTO newPokemon = pokemonService.createPokemon(pokemonModel);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newPokemon.getDexId())).body(newPokemon);
    }

    @PutMapping("/{dexId}/gif/upload")
    public ResponseEntity<PokemonResponseDTO> uploadShinyImg(@PathVariable Long dexId, @RequestParam("file") MultipartFile file) {
        PokemonResponseDTO shinyImg = pokemonService.uploadGif(dexId, file);
        return new ResponseEntity<>(shinyImg, HttpStatus.OK);
    }

    // admin dus?
    @PutMapping("/{dexId}")
    public ResponseEntity<PokemonResponseDTO> updatePokemon(@PathVariable Long id, @RequestBody @Valid PokemonRequestDTO pokemonModel) {
        PokemonResponseDTO updatedPokemon = pokemonService.updatePokemon(id, pokemonModel);
        return new ResponseEntity<>(updatedPokemon, HttpStatus.OK);
    }

    // ook admin dus?
    @DeleteMapping("/{dexId}")
    public ResponseEntity<Void> deletePokemon(@PathVariable Long dexId) {
        pokemonService.deletePokemon(dexId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
