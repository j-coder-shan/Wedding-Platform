package lk.wedrent.wedrent_backend.service;

import lk.wedrent.wedrent_backend.dto.ListingSearchRequest;
import lk.wedrent.wedrent_backend.entity.Listing;
import lk.wedrent.wedrent_backend.repository.ListingRepository;
import lk.wedrent.wedrent_backend.repository.VendorRepository;
import lk.wedrent.wedrent_backend.service.impl.ListingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListingServiceSearchTestFixed {

    @Mock
    private ListingRepository listingRepository;
    
    @Mock
    private VendorRepository vendorRepository;
    
    private ListingServiceImpl listingService;

    @BeforeEach
    void setUp() {
        listingService = new ListingServiceImpl(listingRepository, vendorRepository);
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void testSearchWithFilters() {
        // Mock repository response
        List<Listing> listings = new ArrayList<>();
        Page<Listing> mockPage = new PageImpl<>(listings);
        
        // Mock the repository call with proper generics
        when(listingRepository.findAll((Specification<Listing>) any(Specification.class), any(Pageable.class)))
            .thenReturn(mockPage);
        
        // Create search request using setters
        ListingSearchRequest request = new ListingSearchRequest();
        request.setCategory("DRESS");
        request.setCity("Colombo");
        request.setMinPrice(BigDecimal.valueOf(1000));
        request.setMaxPrice(BigDecimal.valueOf(5000));
        request.setMinRating(4.0);
        request.setPage(0);
        request.setSize(10);
        request.setSort("id,desc");
        
        // Execute search
        Page<Listing> result = listingService.search(request);
        
        // Verify results
        assertEquals(0, result.getTotalElements());
    }
}