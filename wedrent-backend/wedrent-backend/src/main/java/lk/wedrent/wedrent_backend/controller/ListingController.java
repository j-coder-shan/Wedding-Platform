package lk.wedrent.wedrent_backend.controller;

import jakarta.validation.Valid;
import lk.wedrent.wedrent_backend.dto.ListingRequest;
import lk.wedrent.wedrent_backend.dto.ListingResponse;
import lk.wedrent.wedrent_backend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {
    
    private final ListingService listingService;
    
    @PostMapping
    public ResponseEntity<ListingResponse> createListing(
            @RequestParam Long vendorId,
            @Valid @RequestBody ListingRequest request) {
        ListingResponse response = listingService.createListing(vendorId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ListingResponse> updateListing(
            @PathVariable Long id,
            @Valid @RequestBody ListingRequest request) {
        ListingResponse response = listingService.updateListing(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ListingResponse> getListingById(@PathVariable Long id) {
        ListingResponse response = listingService.getListingById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<Page<ListingResponse>> getAllListings(Pageable pageable) {
        Page<ListingResponse> listings = listingService.getAllListings(pageable);
        return ResponseEntity.ok(listings);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<ListingResponse>> searchListings(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            Pageable pageable) {
        Page<ListingResponse> listings = listingService.searchListings(category, location, pageable);
        return ResponseEntity.ok(listings);
    }
}
