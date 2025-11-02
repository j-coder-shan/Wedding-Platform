package lk.wedrent.wedrent_backend.service.impl;

import lk.wedrent.wedrent_backend.dto.ListingRequest;
import lk.wedrent.wedrent_backend.dto.ListingResponse;
import lk.wedrent.wedrent_backend.dto.ListingSearchRequest;
import lk.wedrent.wedrent_backend.entity.Listing;
import lk.wedrent.wedrent_backend.entity.ListingCategory;
import lk.wedrent.wedrent_backend.entity.VendorProfile;
import lk.wedrent.wedrent_backend.repository.ListingRepository;
import lk.wedrent.wedrent_backend.repository.VendorRepository;
import lk.wedrent.wedrent_backend.service.ListingService;
import lk.wedrent.wedrent_backend.specification.ListingSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public ListingServiceImpl(ListingRepository listingRepository, VendorRepository vendorRepository) {
        this.listingRepository = listingRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public ListingResponse createListing(ListingRequest request) {
        VendorProfile vendor = vendorRepository.findById(request.getVendorId())
            .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        Listing listing = new Listing();
        listing.setVendor(vendor);
        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setCategory(request.getCategory());
        listing.setLocation(request.getLocation());
        listing.setPrice(request.getPrice());
        listing.setCurrency(request.getCurrency() != null ? request.getCurrency() : "LKR");
        Listing saved = listingRepository.save(listing);
        return toDto(saved);
    }

    @Override
    public ListingResponse getListing(Long id) {
        Listing l = listingRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        return toDto(l);
    }

    @Override
    public Page<ListingResponse> search(String q, String location, String category, Pageable pageable) {
        if (category != null) {
            ListingCategory cat = ListingCategory.valueOf(category.toUpperCase());
            Page<Listing> page = listingRepository.findByCategory(cat, pageable);
            return page.map(this::toDto);
        }
        if (location != null) {
            Page<Listing> page = listingRepository.findByLocationContainingIgnoreCase(location, pageable);
            return page.map(this::toDto);
        }
        Page<Listing> page = listingRepository.findAll(pageable);
        return page.map(this::toDto);
    }

    @Override
    public ListingResponse updateListing(Long id, ListingRequest request) {
        Listing listing = listingRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        if (request.getTitle() != null) listing.setTitle(request.getTitle());
        if (request.getDescription() != null) listing.setDescription(request.getDescription());
        if (request.getCategory() != null) listing.setCategory(request.getCategory());
        if (request.getLocation() != null) listing.setLocation(request.getLocation());
        if (request.getPrice() != null) listing.setPrice(request.getPrice());
        listing.setUpdatedAt(java.time.Instant.now());
        return toDto(listingRepository.save(listing));
    }

    @Override
    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "listingsSearch", key = "#req.category + ':' + #req.city + ':' + #req.minPrice + ':' + #req.maxPrice + ':' + #req.minRating + ':' + #req.page + ':' + #req.size + ':' + #req.sort")
    public Page<Listing> search(ListingSearchRequest req) {
        int page = req.getPage() != null && req.getPage() >= 0 ? req.getPage() : 0;
        int size = req.getSize() != null && req.getSize() > 0 ? req.getSize() : 20;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        if (req.getSort() != null && !req.getSort().isBlank()) {
            String[] parts = req.getSort().split(",");
            Sort.Direction dir = parts.length > 1 && "asc".equalsIgnoreCase(parts[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
            sort = Sort.by(dir, parts[0]);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        var spec = ListingSpecification.filter(req.getCategoryEnum(), req.getCity(), req.getMinPrice(), req.getMaxPrice(), req.getMinRating());
        return listingRepository.findAll(spec, pageable);
    }

    private ListingResponse toDto(Listing l) {
        ListingResponse r = new ListingResponse();
        r.setId(l.getId());
        r.setVendorId(l.getVendor() != null ? l.getVendor().getId() : null);
        r.setTitle(l.getTitle());
        r.setDescription(l.getDescription());
        r.setCategory(l.getCategory());
        r.setLocation(l.getLocation());
        r.setPrice(l.getPrice());
        r.setCurrency(l.getCurrency());
        // Set default values for fields that may not exist in current entity
        r.setStatus("ACTIVE");  // Default status
        r.setAvgRating(0.0);    // Default rating
        r.setReviewCount(0);    // Default review count
        r.setAvailable(true);   // Default availability
        r.setCreatedAt(l.getCreatedAt());
        r.setUpdatedAt(l.getUpdatedAt());
        return r;
    }
}
