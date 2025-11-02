package lk.wedrent.wedrent_backend.repository;

import lk.wedrent.wedrent_backend.entity.Listing;
import lk.wedrent.wedrent_backend.entity.ListingCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    
    Page<Listing> findByCategory(ListingCategory category, Pageable pageable);
    
    Page<Listing> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    
    Page<Listing> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
