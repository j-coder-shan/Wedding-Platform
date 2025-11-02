package lk.wedrent.wedrent_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "listings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Listing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private VendorProfile vendor;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 2000)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListingCategory category;
    
    private String location;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(length = 3)
    private String currency = "LKR";
    
    @Column(nullable = false)
    private Boolean available = true;
    
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
    
    private Instant updatedAt;
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
