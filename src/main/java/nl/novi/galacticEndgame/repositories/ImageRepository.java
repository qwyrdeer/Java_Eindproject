package nl.novi.galacticEndgame.repositories;

import nl.novi.galacticEndgame.entities.ImageEntity;
import nl.novi.galacticEndgame.enums.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByImageType(ImageType imageType);

}
