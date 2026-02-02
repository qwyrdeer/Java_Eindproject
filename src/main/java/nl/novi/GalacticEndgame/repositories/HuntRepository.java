package nl.novi.GalacticEndgame.repositories;

import nl.novi.GalacticEndgame.entities.HuntEntity;
import nl.novi.GalacticEndgame.entities.PokemonEntity;
import nl.novi.GalacticEndgame.enums.HuntStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HuntRepository extends JpaRepository<HuntEntity, Long> {

    List<HuntEntity> findByHuntStatus(HuntStatus status);
    List<HuntEntity> findByUser_UserIdAndHuntStatus(Long userId, HuntStatus status);
    List<HuntEntity> findHuntsByUser_UserId(Long userId);
    List<HuntEntity> findHuntsByPokemon(PokemonEntity pokemon);

    // is voorgesteld, maar nog ff checken \/
    @Override
    <S extends HuntEntity> S save(S entity);

    @Override
    void delete(HuntEntity entity);
}
