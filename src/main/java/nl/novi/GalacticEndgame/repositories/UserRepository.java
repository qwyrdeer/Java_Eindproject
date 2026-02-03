package nl.novi.GalacticEndgame.repositories;

import nl.novi.GalacticEndgame.entities.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findById(Long userId);
}
