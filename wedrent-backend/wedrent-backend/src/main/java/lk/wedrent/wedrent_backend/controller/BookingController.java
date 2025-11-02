package lk.wedrent.wedrent_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lk.wedrent.wedrent_backend.dto.BookingRequest;
import lk.wedrent.wedrent_backend.dto.BookingResponse;
import lk.wedrent.wedrent_backend.entity.Booking;
import lk.wedrent.wedrent_backend.entity.BookingStatus;
import lk.wedrent.wedrent_backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) { this.bookingService = bookingService; }

    @Operation(summary = "Create a booking (authenticated users)")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Validated BookingRequest req, @RequestHeader("X-USER-ID") Long userId) {
        Booking b = bookingService.createBooking(userId, req);
        var resp = new BookingResponse(b.getId(), b.getUser().getId(), b.getListing().getId(), b.getStartDate(), b.getEndDate(), b.getStatus(), b.getTotalPrice());
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Update booking status (vendors/admins)")
    @PreAuthorize("hasAnyRole('VENDOR','ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingResponse> updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        Booking b = bookingService.updateStatus(id, status);
        var resp = new BookingResponse(b.getId(), b.getUser().getId(), b.getListing().getId(), b.getStartDate(), b.getEndDate(), b.getStatus(), b.getTotalPrice());
        return ResponseEntity.ok(resp);
    }
}
