package nl.novi.GalacticEndgame.controllers;

import jakarta.validation.Valid;
import nl.novi.GalacticEndgame.dtos.hunt.HuntRequestDTO;
import nl.novi.GalacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.GalacticEndgame.enums.HuntStatus;
import nl.novi.GalacticEndgame.helpers.UrlHelper;
import nl.novi.GalacticEndgame.services.HuntService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/hunts")
public class HuntController {

    private final HuntService huntService;
    private final UrlHelper urlHelper;

    public HuntController(HuntService huntService, UrlHelper urlHelper) {
        this.huntService = huntService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<HuntResponseDTO>> getAllHunts() {
        List<HuntResponseDTO> hunts = huntService.findAllHunts();
        return new ResponseEntity<>(hunts, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<HuntResponseDTO> getHuntsByUser_UserId(@PathVariable Long userId) {
        HuntResponseDTO hunt = huntService.findHuntById(userId);
        return new ResponseEntity<>(hunt, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<HuntResponseDTO>> getHuntsByStatus(@PathVariable HuntStatus status) {
        List<HuntResponseDTO> hunts = huntService.findHuntsByStatus(status);
        return new ResponseEntity<>(hunts, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<HuntResponseDTO>> findHuntsByUserAndStatus(@PathVariable Long userId, @PathVariable HuntStatus status) {
        List<HuntResponseDTO> userStatusHunts = huntService.findHuntsByUserAndStatus(userId, status);
        return new ResponseEntity<>(userStatusHunts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HuntResponseDTO> getHuntById(@PathVariable Long id) {
        HuntResponseDTO hunt = huntService.findHuntById(id);
        return new ResponseEntity<>(hunt, HttpStatus.OK);
    }

//    @ModelAttribute

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HuntResponseDTO> createHunt(@RequestPart("data") @Valid HuntRequestDTO data, @RequestPart(value = "shinyImg", required = false) MultipartFile shinyImg) {
        HuntResponseDTO hunt = huntService.createHunt(data, shinyImg);
        return new ResponseEntity<>(hunt, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HuntResponseDTO> updateHunt(@PathVariable Long id, @RequestBody @Valid HuntRequestDTO huntModel) {
        HuntResponseDTO updatedHunt = huntService.updateHunt(id, huntModel);;
        return new ResponseEntity<>(updatedHunt, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteHunt(@PathVariable Long id) {
        huntService.deleteHunt(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
