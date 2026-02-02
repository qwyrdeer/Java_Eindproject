package nl.novi.GalacticEndgame.repositories;

import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.enums.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findByImageType(ImageType imageType);

}
