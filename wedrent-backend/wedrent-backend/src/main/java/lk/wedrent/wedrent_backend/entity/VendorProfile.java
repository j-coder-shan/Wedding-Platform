package lk.wedrent.wedrent_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "vendor_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(nullable = false)
    private String businessName;
    
    private String address;
    
    private String contactNumber;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private Boolean verified = false;
    
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
}
