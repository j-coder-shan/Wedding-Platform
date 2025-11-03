package lk.wedrent.wedrent_backend.repository;

import lk.wedrent.wedrent_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // custom queries for availability checks can be added later
}
