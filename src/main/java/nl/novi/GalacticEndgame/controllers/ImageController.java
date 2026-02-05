package nl.novi.GalacticEndgame.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import nl.novi.GalacticEndgame.enums.ImageType;
import nl.novi.GalacticEndgame.services.ImageService;
import nl.novi.GalacticEndgame.services.UserService;
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

    private final ImageService imageService;
    private final UserService userService;

    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable Long userId, HttpServletRequest request) {
        Resource resource = userService.getUserAvatar(userId);
        return getImageResponse(id, ImageType.AVATAR, request);
    }

    @GetMapping("/{id}/pkmn-gif")
    public ResponseEntity<Resource> getPokemonGif(@PathVariable Long id, HttpServletRequest request) {

        return getImageResponse(id, ImageType.PKMN_GIF, request);
    }

}
