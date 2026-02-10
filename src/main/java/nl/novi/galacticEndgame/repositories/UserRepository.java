package nl.novi.galacticEndgame.repositories;

import nl.novi.galacticEndgame.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(Long userId);
    Optional<UserEntity> findUserByUsernameIgnoreCase(String username);
    List<UserEntity> findAll();

    @Override
    void delete(UserEntity entity);

    UserEntity save(UserEntity userEntity);
}
