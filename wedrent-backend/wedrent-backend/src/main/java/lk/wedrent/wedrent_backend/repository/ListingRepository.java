package lk.wedrent.wedrent_backend.repository;

import lk.wedrent.wedrent_backend.entity.Listing;
import lk.wedrent.wedrent_backend.entity.ListingCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {

    Page<Listing> findByCategory(ListingCategory category, Pageable pageable);

    Page<Listing> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    Page<Listing> findByStatus(String status, Pageable pageable);

    default Page<Listing> findAllActive(Pageable pageable) {
        return findByStatus("ACTIVE", pageable);
    }

    @Query("SELECT l FROM Listing l WHERE l.vendor.id = :vendorId")
    Page<Listing> findByVendorId(@Param("vendorId") Long vendorId, Pageable pageable);

    // Compute average rating from reviews (requires Listing.reviews association with 'rating' field)
    @Query("""
        SELECT l FROM Listing l
        LEFT JOIN l.reviews r
        GROUP BY l
        HAVING COALESCE(AVG(r.rating), 0) >= :minRating
        """)
    Page<Listing> findByMinRating(@Param("minRating") Double minRating, Pageable pageable);
}
