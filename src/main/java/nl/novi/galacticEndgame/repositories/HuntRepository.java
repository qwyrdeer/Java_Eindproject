package nl.novi.galacticEndgame.repositories;

import nl.novi.galacticEndgame.entities.HuntEntity;
import nl.novi.galacticEndgame.enums.HuntStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HuntRepository extends JpaRepository<HuntEntity, Long> {

    List<HuntEntity> findByHuntStatus(HuntStatus status);
    List<HuntEntity> findByUserEntity_UserIdAndHuntStatus(Long userId, HuntStatus status);
    List<HuntEntity> findByUserEntity_UserId(Long userId);
    List<HuntEntity> findByUserEntity_UsernameIgnoreCase(String username);
    List<HuntEntity> findByPokemon_NameIgnoreCase(String name);

    @Override
    <S extends HuntEntity> S save(S entity);

    @Override
    void delete(HuntEntity entity);

}
