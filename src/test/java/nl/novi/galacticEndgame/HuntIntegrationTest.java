package nl.novi.galacticEndgame;

import jakarta.transaction.Transactional;
import nl.novi.galacticEndgame.entities.PokemonEntity;
import nl.novi.galacticEndgame.entities.UserEntity;
import nl.novi.galacticEndgame.enums.UserRole;
import nl.novi.galacticEndgame.repositories.HuntRepository;
import nl.novi.galacticEndgame.repositories.PokemonRepository;
import nl.novi.galacticEndgame.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters= false)
@ActiveProfiles("test")
@Transactional
class HuntIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private HuntRepository huntRepository;

    private Long userId;

    @BeforeEach
    void setup() {
        huntRepository.deleteAll();
        pokemonRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = new UserEntity();
        user.setUsername("Ash");
        user.setUserRole(UserRole.ADMIN);
        user = userRepository.save(user);
        userId = user.getUserId();

        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setDexId(133L);
        pokemon.setName("Eevee");
        pokemon.setHuntCount(200L);
        pokemonRepository.save(pokemon);
    }

    @Test
    void huntShouldBeCreated() throws Exception {
        String huntJson = """
    {
      "userId": 1,
      "dexId": 133,
      "name": "Eevee",
      "usedGame": "Scarlet",
      "huntMethod": "Random Encounters",
      "encounters": 250,
      "huntStatus": "CURRENT"
    }
    """;

        MockMultipartFile jsonPart = new MockMultipartFile(
                "data",
                "",
                "application/json",
                huntJson.getBytes()
        );

        mockMvc.perform(multipart("/hunts")
                        .file(jsonPart))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pokemon.dexId").value(133))
                .andExpect(jsonPath("$.pokemon.name").value("Eevee"))
                .andExpect(jsonPath("$.usedGame").value("Scarlet"))
                .andExpect(jsonPath("$.huntMethod").value("Random Encounters"))
                .andExpect(jsonPath("$.encounters").value(250))
                .andExpect(jsonPath("$.huntStatus").value("CURRENT"));
    }}