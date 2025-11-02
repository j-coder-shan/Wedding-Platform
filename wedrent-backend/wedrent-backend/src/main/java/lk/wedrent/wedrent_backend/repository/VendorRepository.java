package lk.wedrent.wedrent_backend.repository;

import lk.wedrent.wedrent_backend.entity.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<VendorProfile, Long> {
}
