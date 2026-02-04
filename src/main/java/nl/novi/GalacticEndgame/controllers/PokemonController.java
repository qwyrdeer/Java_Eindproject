package nl.novi.GalacticEndgame.controllers;

import nl.novi.GalacticEndgame.helpers.UrlHelper;
import nl.novi.GalacticEndgame.services.PokemonService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
