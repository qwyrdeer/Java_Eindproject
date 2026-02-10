package nl.novi.galacticEndgame.dtos.image;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ImageRequestDTO {

    private static final long MAX_BYTES = 2_000_000;

    @NotBlank(message = "originalFileName is required")
    private String originalFileName;
    private String url;

    @NotBlank(message = "contentType is required")
    @Pattern(regexp = "^image/(png|jpeg|jpg|gif|webp)$",
            message = "contentType must be a valid image type (png, jpeg/jpg, gif, webp)")
    private String contentType;

    // kan ik twee upload links maken waarbij dit wordt gespecificeerd/toegevoegd > GIF of AVA?
    @NotBlank(message = "imageType is required")
    @Pattern(regexp = "^(AVATAR|POKEMON_GIF)$",
            message = "imageType must be one of: AVATAR or POKEMON_GIF")
    private String imageType;

    @NotNull(message = "size is required")
    @Max(value = MAX_BYTES, message = "size must be <= 2MB")
    private Long size;

    private String path;

    // Getter setter

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
