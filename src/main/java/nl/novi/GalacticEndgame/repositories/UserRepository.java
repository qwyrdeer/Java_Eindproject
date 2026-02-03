package nl.novi.GalacticEndgame.repositories;

import nl.novi.GalacticEndgame.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long userId);
    Optional<UserEntity> findUserByUsernameIgnoreCase(String username);
    List<UserEntity> findAll();

    boolean existenceCheckUsernameIgnoreCase(String username);

    UserEntity save(UserEntity userEntity);
}
