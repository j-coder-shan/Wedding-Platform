package lk.wedrent.wedrent_backend.repository;

import lk.wedrent.wedrent_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
