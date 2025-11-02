package lk.wedrent.wedrent_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "listings", indexes = {
        @Index(columnList = "title", name = "idx_listing_title"),
        @Index(columnList = "category", name = "idx_listing_category"),
        @Index(columnList = "location", name = "idx_listing_location")
})
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendor_id")
    private VendorProfile vendor;

    private String title;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private ListingCategory category;

    private String location;

    private BigDecimal price;

    private String currency = "LKR";

    private boolean available = true;

    private Instant createdAt = Instant.now();
    private Instant updatedAt;

    // NEW: persisted status (or change to enum if preferred)
    @Column(name = "status")
    private String status = "ACTIVE";

    // NEW: optional persisted average rating (keeps compatibility; repositories can compute when needed)
    @Column(name = "avg_rating")
    private Double avgRating;

    // NEW: reviews association used by JPQL queries that compute averages
    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public VendorProfile getVendor() { return vendor; }
    public void setVendor(VendorProfile vendor) { this.vendor = vendor; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ListingCategory getCategory() { return category; }
    public void setCategory(ListingCategory category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getAvgRating() { return avgRating; }
    public void setAvgRating(Double avgRating) { this.avgRating = avgRating; }

    public Set<Review> getReviews() { return reviews; }
    public void setReviews(Set<Review> reviews) { this.reviews = reviews; }
}
