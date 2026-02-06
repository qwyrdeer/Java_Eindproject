package nl.novi.GalacticEndgame.controllers;

import jakarta.servlet.http.HttpServletRequest;
import nl.novi.GalacticEndgame.services.ImageService;
import nl.novi.GalacticEndgame.services.PokemonService;
import nl.novi.GalacticEndgame.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final UserService userService;
    private final PokemonService pokemonService;

    public ImageController(UserService userService, PokemonService pokemonService) {
        this.userService = userService;
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable Long userId, HttpServletRequest request) {
        Resource resource = userService.loadUserAvatar(userId);
        String mimeType;

        try {
            mimeType = request
                    .getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\""
                )
                .body(resource);
    }

    @GetMapping("/{dexId}/pkmn-gif")
    public ResponseEntity<Resource>  getShinyImg(@PathVariable Long dexId, HttpServletRequest request) {
        Resource resource = (Resource) pokemonService.loadShinyImg(dexId);
        String mimeType;
        try {
            mimeType = request
                    .getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\""
                )
                .body(resource);
    }

}
