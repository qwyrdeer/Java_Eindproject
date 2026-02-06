package nl.novi.GalacticEndgame.services;

import nl.novi.GalacticEndgame.entities.ImageEntity;
import nl.novi.GalacticEndgame.enums.ImageType;
import nl.novi.GalacticEndgame.exeptions.ImageNotFoundException;
import nl.novi.GalacticEndgame.exeptions.StoringException;
import nl.novi.GalacticEndgame.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${my.upload_location}")
    private final Path fileStoragePath;
    private final ImageRepository repo;

    public ImageService(@Value("${my.upload_location}") String fileStorageLocation, ImageRepository repo) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.repo = repo;

        Files.createDirectories(fileStoragePath);
    }

    public ImageEntity storeImage(MultipartFile file, ImageType type) {

        String original = saveOriginalName(file.getOriginalFilename());
        String ext = extension(original, file.getContentType());
        String stored = UUID.randomUUID() + ext;

        String subfolder = (type == ImageType.AVATAR) ? "AVATAR" : "PKMN_GIF";

        Path target = fileStoragePath.resolve(subfolder).resolve(stored).normalize();

        try {Files.createDirectories(target.getParent());
            file.transferTo(target.toFile());
        } catch (IOException e) {
            throw new StoringException("Could not store file", e);
        }

        ImageEntity image = new ImageEntity();
        image.setOriginalName(original);
        image.setStoredName(stored);
        image.setContentType(file.getContentType());
        image.setUrl(subfolder + "/" + stored);
        image.setSize(file.getSize());
        image.setImageType(type);
        image.setPath(subfolder + "/" + stored);

        return repo.save(image);
    }

    private String saveOriginalName(String name) {
        if (name.isBlank()) return "upload";
        return Paths.get(name).getFileName().toString();
    }

    private String extension(String original, String contentType) {
        int dot = original.lastIndexOf('.');
        if (dot > -1 && dot < original.length() - 1) {
            return original.substring(dot).toLowerCase();
        }

        return switch (contentType) {
            case "image/png" -> ".png";
            case "image/jpeg" -> ".jpg";
            case "image/gif" -> ".gif";
            case "image/webp" -> ".webp";
            default -> "";
        };
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = fileStoragePath.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                throw new ImageNotFoundException("Image not found: " + filename);
            }
            return resource;

        } catch (Exception e) {
            throw new RuntimeException("File not found " + filename);
        }
    }
}
