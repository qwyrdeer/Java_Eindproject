package nl.novi.GalacticEndgame.repositories;

import nl.novi.GalacticEndgame.entities.HuntEntity;
import nl.novi.GalacticEndgame.enums.HuntStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HuntRepository extends JpaRepository<HuntEntity, Long> {

    List<HuntEntity> findByHuntStatus(HuntStatus status);
    List<HuntEntity> findByUserEntity_UserIdAndHuntStatus(Long userId, HuntStatus status);
    List<HuntEntity> findByUserEntity_UserId(Long userId);
    List<HuntEntity> findByUserEntity_UsernameIgnoreCase(String username);
    List<HuntEntity> findByPokemon_NameIgnoreCase(String name);

    // is voorgesteld, maar nog ff checken \/
    @Override
    <S extends HuntEntity> S save(S entity);

    @Override
    void delete(HuntEntity entity);

}
