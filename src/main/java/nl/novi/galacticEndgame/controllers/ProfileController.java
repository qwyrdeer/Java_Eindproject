package nl.novi.galacticEndgame.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.galacticEndgame.dtos.profile.ProfileRequestDTO;
import nl.novi.galacticEndgame.dtos.profile.ProfileResponseDTO;
import nl.novi.galacticEndgame.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "profiles", description = "Manage profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile/{userId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "find profile by userId")
    public ResponseEntity<ProfileResponseDTO> findProfileByUser_UserId(@PathVariable Long userId) {
       ProfileResponseDTO profile = profileService.findProfileByUser_UserId(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/profile/{username}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "find profile by username")
    public ResponseEntity<ProfileResponseDTO> findProfileByUser_UsernameIgnoreCase(@PathVariable String username) {
        ProfileResponseDTO profile = profileService.findProfileByUser_UsernameIgnoreCase(username);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/profile/update/{userId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "update profile by id")
    public ResponseEntity<ProfileResponseDTO> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody ProfileRequestDTO dto) {
        ProfileResponseDTO profile = profileService.updateProfileByUserId(userId, dto);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

}
