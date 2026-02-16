package nl.novi.galacticEndgame.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.galacticEndgame.dtos.user.UserRequestDTO;
import nl.novi.galacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.galacticEndgame.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "users", description = "Manage users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long userId) {
        UserResponseDTO user = userService.findUserByUserId(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponseDTO> findUserByUser_UsernameIgnoreCase(@PathVariable String username) {
        UserResponseDTO user = userService.findUserByUser_UsernameIgnoreCase(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "find all users")
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me")
    @Operation(summary = "find yourself")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        UserResponseDTO user = userService.findOrCreateUser(authentication);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/me/avatar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "upload your avatar")
    public ResponseEntity<UserResponseDTO> uploadAvatar(Authentication authentication, @RequestParam("file") MultipartFile file) {
        UserResponseDTO avatar = userService.uploadAvatar(authentication, file);
        return new ResponseEntity<>(avatar, HttpStatus.OK);
    }

    @DeleteMapping("/id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
