package nl.novi.GalacticEndgame.controllers;

import jakarta.validation.Valid;
import nl.novi.GalacticEndgame.dtos.profile.ProfileRequestDTO;
import nl.novi.GalacticEndgame.dtos.profile.ProfileResponseDTO;
import nl.novi.GalacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.GalacticEndgame.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileResponseDTO> findProfileByUser_UserId(@PathVariable Long userId) {
       ProfileResponseDTO profile = profileService.findProfileByUser_UserId(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/profiles/{username}")
    public ResponseEntity<ProfileResponseDTO> findProfileByUser_UsernameIgnoreCase(@PathVariable String username) {
        ProfileResponseDTO profile = profileService.findProfileByUser_UsernameIgnoreCase(username);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/profile/update/{userId}")
    public ResponseEntity<ProfileResponseDTO> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody ProfileRequestDTO dto) {
        ProfileResponseDTO profile = profileService.updateProfileByUserId(userId, dto);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }


}
