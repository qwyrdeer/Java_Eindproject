package nl.novi.GalacticEndgame.dtos.user;

//import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

    // wat hiervan regel ik straks in keycloak?

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 25, message = "Username must be between 3 and 25 characters")
    private String username;

//    @NotBlank(message = "Email is required")
//    @Email(message = "Email must be valid")
//    private String email;
//
//    @NotBlank(message = "Password is required")
//    @Size(min = 8, message = "Password must be at least 8 characters long")
//    private String password;

    // -- getter en setter


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
