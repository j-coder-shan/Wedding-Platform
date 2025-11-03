package lk.wedrent.wedrent_backend.service;

import lk.wedrent.wedrent_backend.dto.BookingRequest;
import lk.wedrent.wedrent_backend.entity.Booking;
import lk.wedrent.wedrent_backend.entity.BookingStatus;
import lk.wedrent.wedrent_backend.entity.Listing;
import lk.wedrent.wedrent_backend.entity.User;
import lk.wedrent.wedrent_backend.repository.BookingRepository;
import lk.wedrent.wedrent_backend.repository.ListingRepository;
import lk.wedrent.wedrent_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ListingRepository listingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Booking createBooking(Long userId, BookingRequest req) {
        User user = userRepository.findById(userId).orElseThrow();
        Listing listing = listingRepository.findById(req.listingId()).orElseThrow();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setListing(listing);
        booking.setStartDate(req.startDate());
        booking.setEndDate(req.endDate());
        booking.setTotalPrice(req.totalPrice());
        booking.setStatus(BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking updateStatus(Long bookingId, BookingStatus status) {
        Booking b = bookingRepository.findById(bookingId).orElseThrow();
        b.setStatus(status);
        return bookingRepository.save(b);
    }

    // other helpers: approve, cancel, complete can be added similarly
}
