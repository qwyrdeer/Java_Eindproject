package nl.novi.GalacticEndgame.repositories;

import nl.novi.GalacticEndgame.entities.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<PokemonEntity, Long> {

    Optional<PokemonEntity> findByDexId(Long dexId);
    Optional<PokemonEntity> findByName(String name);
    List<PokemonEntity> findAll();

    // is voorgesteld, maar nog ff checken \/
    @Override
    <S extends PokemonEntity> S save(S entity);
}
