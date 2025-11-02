package lk.wedrent.wedrent_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lk.wedrent.wedrent_backend.entity.Image;
import lk.wedrent.wedrent_backend.repository.ImageRepository;
import lk.wedrent.wedrent_backend.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageStorageService storage;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageStorageService storage, ImageRepository imageRepository) {
        this.storage = storage;
        this.imageRepository = imageRepository;
    }

    @Operation(summary = "Upload multiple images for a listing")
    @PreAuthorize("hasAnyRole('VENDOR','ADMIN')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Image>> uploadImages(@RequestParam("listingId") Long listingId, @RequestPart("files") List<MultipartFile> files) {
        // validate file types and size (example: max 5MB)
        var valid = files.stream().allMatch(f -> {
            var ct = f.getContentType();
            var sizeOk = f.getSize() <= 5L * 1024 * 1024;
            var imageType = ct != null && (ct.equals(MimeTypeUtils.IMAGE_JPEG_VALUE) || ct.equals(MimeTypeUtils.IMAGE_PNG_VALUE));
            return sizeOk && imageType;
        });
        if (!valid) {
            return ResponseEntity.badRequest().build();
        }

        var urls = storage.uploadMany(files, "listings/" + listingId);
        var saved = urls.stream().map(url -> {
            var img = new Image();
            img.setUrl(url);
            img.setContentType("image");
            img.setSizeBytes(0L);
            img.setUploadedAt(OffsetDateTime.now());
            // linking to listing is left to application logic or repository; assume listing exists and set by client later
            img.setListing(null);
            return imageRepository.save(img);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(saved);
    }
}
