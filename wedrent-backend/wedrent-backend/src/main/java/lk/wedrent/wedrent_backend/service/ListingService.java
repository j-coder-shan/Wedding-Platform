package lk.wedrent.wedrent_backend.service;

import lk.wedrent.wedrent_backend.dto.ListingRequest;
import lk.wedrent.wedrent_backend.dto.ListingResponse;
import lk.wedrent.wedrent_backend.dto.ListingSearchRequest;
import lk.wedrent.wedrent_backend.entity.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingService {
    
    ListingResponse createListing(ListingRequest request);
    
    ListingResponse getListing(Long id);
    
    Page<ListingResponse> search(String q, String location, String category, Pageable pageable);
    
    Page<Listing> search(ListingSearchRequest req);
    
    ListingResponse updateListing(Long id, ListingRequest request);
    
    void deleteListing(Long id);
}
