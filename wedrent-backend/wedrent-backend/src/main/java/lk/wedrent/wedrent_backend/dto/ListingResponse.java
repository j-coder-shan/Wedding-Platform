package lk.wedrent.wedrent_backend.dto;

import lk.wedrent.wedrent_backend.entity.ListingCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingResponse {
    
    private Long id;
    private Long vendorId;
    private String vendorName;
    private String title;
    private String description;
    private ListingCategory category;
    private String location;
    private BigDecimal price;
    private String currency;
    private Boolean available;
    private Instant createdAt;
    private Instant updatedAt;
}
