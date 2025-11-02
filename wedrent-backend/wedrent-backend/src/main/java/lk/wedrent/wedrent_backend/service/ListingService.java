package lk.wedrent.wedrent_backend.service;

import lk.wedrent.wedrent_backend.dto.ListingRequest;
import lk.wedrent.wedrent_backend.dto.ListingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingService {
    
    ListingResponse createListing(Long vendorId, ListingRequest request);
    
    ListingResponse updateListing(Long id, ListingRequest request);
    
    void deleteListing(Long id);
    
    ListingResponse getListingById(Long id);
    
    Page<ListingResponse> getAllListings(Pageable pageable);
    
    Page<ListingResponse> searchListings(String category, String location, Pageable pageable);
}
