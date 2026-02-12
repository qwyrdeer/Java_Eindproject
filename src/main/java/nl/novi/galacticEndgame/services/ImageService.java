package nl.novi.galacticEndgame.services;

import nl.novi.galacticEndgame.entities.ImageEntity;
import nl.novi.galacticEndgame.enums.ImageType;
import nl.novi.galacticEndgame.exeptions.ImageNotFoundException;
import nl.novi.galacticEndgame.exeptions.IncorrectInputException;
import nl.novi.galacticEndgame.exeptions.StoringException;
import nl.novi.galacticEndgame.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageService {

    private static final Map<String, String> ALLOWED_FILETYPES = Map.of(
            "image/png", ".png",
            "image/jpeg", ".jpg",
            "image/gif", ".gif",
            "image/webp", ".webp"
    );

    private final ImageRepository imageRepository;
    private final Path uploadRoot;

    public ImageService(@Value("${my.upload_location}") String uploadLocation, ImageRepository imageRepository) {
        this.uploadRoot= Paths.get(uploadLocation).toAbsolutePath().normalize();
        this.imageRepository = imageRepository;
    }

    public ImageEntity storeImage(MultipartFile file, ImageType type) {

        if (file == null || file.isEmpty()) {
            throw new IncorrectInputException("No image file uploaded");
        }

        String contentType = file.getContentType();
        String original = saveOriginalName(file.getOriginalFilename());

        if (!ALLOWED_FILETYPES.containsKey(contentType)) {
            throw new IncorrectInputException("Unsupported image type: " + contentType);
        }

        String expectedExt = ALLOWED_FILETYPES.get(contentType);
        if (!original.toLowerCase().endsWith(expectedExt)) {
            throw new IncorrectInputException(
                    "File extension does not match content type"
            );
        }
        String ext = extension(original, file.getContentType());
        String stored = UUID.randomUUID() + ext;

        String subfolder = (type == ImageType.AVATAR) ? "avatars" : "pkmn_gifs";

        Path target = uploadRoot.resolve(subfolder).resolve(stored).normalize();

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

        return imageRepository.save(image);
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
            Path file = uploadRoot.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                throw new ImageNotFoundException("Image not found: " + filename);
            }
            return resource;

        } catch (Exception e) {
            throw new RuntimeException("File not found " + filename);
        }
    }

    public void deleteByUrl(String url) {
        if (url == null || url.isBlank()) return;
        String relative = url.startsWith("/") ? url.substring(1) : url;
        Path path = Paths.get(relative).normalize();
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StoringException("Could not delete old file: " + url, e);
        }
    }
}
