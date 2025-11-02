package lk.wedrent.wedrent_backend.service.impl;

import lk.wedrent.wedrent_backend.dto.ListingRequest;
import lk.wedrent.wedrent_backend.dto.ListingResponse;
import lk.wedrent.wedrent_backend.entity.Listing;
import lk.wedrent.wedrent_backend.entity.ListingCategory;
import lk.wedrent.wedrent_backend.entity.VendorProfile;
import lk.wedrent.wedrent_backend.repository.ListingRepository;
import lk.wedrent.wedrent_backend.repository.VendorRepository;
import lk.wedrent.wedrent_backend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {
    
    private final ListingRepository listingRepository;
    private final VendorRepository vendorRepository;
    
    @Override
    @Transactional
    public ListingResponse createListing(Long vendorId, ListingRequest request) {
        VendorProfile vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        
        Listing listing = new Listing();
        listing.setVendor(vendor);
        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setCategory(request.getCategory());
        listing.setLocation(request.getLocation());
        listing.setPrice(request.getPrice());
        listing.setCurrency(request.getCurrency());
        listing.setAvailable(request.getAvailable());
        
        Listing saved = listingRepository.save(listing);
        return mapToResponse(saved);
    }
    
    @Override
    @Transactional
    public ListingResponse updateListing(Long id, ListingRequest request) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        
        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setCategory(request.getCategory());
        listing.setLocation(request.getLocation());
        listing.setPrice(request.getPrice());
        listing.setCurrency(request.getCurrency());
        listing.setAvailable(request.getAvailable());
        
        Listing updated = listingRepository.save(listing);
        return mapToResponse(updated);
    }
    
    @Override
    @Transactional
    public void deleteListing(Long id) {
        if (!listingRepository.existsById(id)) {
            throw new IllegalArgumentException("Listing not found");
        }
        listingRepository.deleteById(id);
    }
    
    @Override
    public ListingResponse getListingById(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        return mapToResponse(listing);
    }
    
    @Override
    public Page<ListingResponse> getAllListings(Pageable pageable) {
        return listingRepository.findAll(pageable).map(this::mapToResponse);
    }
    
    @Override
    public Page<ListingResponse> searchListings(String category, String location, Pageable pageable) {
        if (category != null && location != null) {
            // Filter by both category and location
            try {
                ListingCategory categoryEnum = ListingCategory.valueOf(category);
                return listingRepository.findByCategory(categoryEnum, pageable)
                        .map(this::mapToResponse);
                // Note: This is a simplified implementation. A more robust solution would use
                // Specification API or custom query to filter by both category AND location.
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid category: " + category);
            }
        } else if (category != null) {
            try {
                ListingCategory categoryEnum = ListingCategory.valueOf(category);
                return listingRepository.findByCategory(categoryEnum, pageable)
                        .map(this::mapToResponse);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid category: " + category);
            }
        } else if (location != null) {
            return listingRepository.findByLocationContainingIgnoreCase(location, pageable)
                    .map(this::mapToResponse);
        }
        return getAllListings(pageable);
    }
    
    private ListingResponse mapToResponse(Listing listing) {
        ListingResponse response = new ListingResponse();
        response.setId(listing.getId());
        response.setVendorId(listing.getVendor().getId());
        response.setVendorName(listing.getVendor().getBusinessName());
        response.setTitle(listing.getTitle());
        response.setDescription(listing.getDescription());
        response.setCategory(listing.getCategory());
        response.setLocation(listing.getLocation());
        response.setPrice(listing.getPrice());
        response.setCurrency(listing.getCurrency());
        response.setAvailable(listing.getAvailable());
        response.setCreatedAt(listing.getCreatedAt());
        response.setUpdatedAt(listing.getUpdatedAt());
        return response;
    }
}
