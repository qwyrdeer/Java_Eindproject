package nl.novi.galacticEndgame.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.galacticEndgame.dtos.hunt.HuntRequestDTO;
import nl.novi.galacticEndgame.dtos.hunt.HuntResponseDTO;
import nl.novi.galacticEndgame.enums.HuntStatus;
import nl.novi.galacticEndgame.helpers.UrlHelper;
import nl.novi.galacticEndgame.services.HuntService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/hunts")
@Tag(name = "Hunts", description = "Manage hunts by the community")
public class HuntController {

    private final HuntService huntService;

    public HuntController(HuntService huntService, UrlHelper urlHelper) {
        this.huntService = huntService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "get all hunts")
    public ResponseEntity<List<HuntResponseDTO>> getAllHunts() {
        List<HuntResponseDTO> hunts = huntService.findAllHunts();
        return new ResponseEntity<>(hunts, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "get all hunts by userId")
    public ResponseEntity<HuntResponseDTO> getHuntsByUser_UserId(@PathVariable Long userId) {
        HuntResponseDTO hunt = huntService.findHuntById(userId);
        return new ResponseEntity<>(hunt, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "get all hunts by status")
    public ResponseEntity<List<HuntResponseDTO>> getHuntsByStatus(@PathVariable HuntStatus status) {
        List<HuntResponseDTO> hunts = huntService.findHuntsByStatus(status);
        return new ResponseEntity<>(hunts, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/status/{status}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "get all hunts with certain status from user")
    public ResponseEntity<List<HuntResponseDTO>> findHuntsByUserAndStatus(@PathVariable Long userId, @PathVariable HuntStatus status) {
        List<HuntResponseDTO> userStatusHunts = huntService.findHuntsByUserAndStatus(userId, status);
        return new ResponseEntity<>(userStatusHunts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "get all hunts by id")
    public ResponseEntity<HuntResponseDTO> getHuntById(@PathVariable Long id) {
        HuntResponseDTO hunt = huntService.findHuntById(id);
        return new ResponseEntity<>(hunt, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "create a hunt")
    public ResponseEntity<HuntResponseDTO> createHunt(
            @RequestPart HuntRequestDTO data,
            @RequestPart(required = false) MultipartFile shinyImg, Authentication authentication) {

        HuntResponseDTO created = huntService.createHunt(data, shinyImg, authentication);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "update a hunt")
    public ResponseEntity<HuntResponseDTO> updateHunt(@PathVariable Long id, @RequestBody @Valid HuntRequestDTO huntModel, Authentication authentication) {
        HuntResponseDTO updatedHunt = huntService.updateHunt(id, huntModel, authentication);
        return new ResponseEntity<>(updatedHunt, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "delete a hunt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteHunt(@PathVariable Long id) {
        huntService.deleteHunt(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
