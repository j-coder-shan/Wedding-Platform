package lk.wedrent.wedrent_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lk.wedrent.wedrent_backend.dto.ListingSearchRequest;
import lk.wedrent.wedrent_backend.entity.Listing;
import lk.wedrent.wedrent_backend.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/listings")
public class ListingController {

    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @Operation(summary = "Search listings with filters, pagination and sorting")
    @GetMapping("/search")
    public ResponseEntity<Page<Listing>> search(@ModelAttribute ListingSearchRequest req) {
        var result = listingService.search(req);
        return ResponseEntity.ok(result);
    }

    // other listing endpoints from Sprint 1 remain
}
