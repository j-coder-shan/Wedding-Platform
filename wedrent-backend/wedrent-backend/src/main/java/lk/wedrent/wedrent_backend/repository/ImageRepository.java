package lk.wedrent.wedrent_backend.repository;

import lk.wedrent.wedrent_backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByListingId(Long listingId);
}
