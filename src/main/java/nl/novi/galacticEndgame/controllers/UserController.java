package nl.novi.galacticEndgame.controllers;

import jakarta.validation.Valid;
import nl.novi.galacticEndgame.dtos.user.UserRequestDTO;
import nl.novi.galacticEndgame.dtos.user.UserResponseDTO;
import nl.novi.galacticEndgame.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
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
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO created = userService.createUser(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/avatar/upload")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponseDTO> uploadAvatar(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        UserResponseDTO avatar = userService.uploadAvatar(userId, file);
        return new ResponseEntity<>(avatar, HttpStatus.OK);
    }

    @DeleteMapping("/id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
