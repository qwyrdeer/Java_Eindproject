package nl.novi.GalacticEndgame.repositories;

import nl.novi.GalacticEndgame.entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByUser_UserId(Long userId);
    Optional<ProfileEntity> findByUser_UsernameIgnoreCase(String username);
}
