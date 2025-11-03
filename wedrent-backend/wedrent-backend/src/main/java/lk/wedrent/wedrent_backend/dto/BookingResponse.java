package lk.wedrent.wedrent_backend.dto;

import lk.wedrent.wedrent_backend.entity.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponse(
    Long id,
    Long userId,
    Long listingId,
    LocalDate startDate,
    LocalDate endDate,
    BookingStatus status,
    BigDecimal totalPrice
) {}
