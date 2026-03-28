package com.restaurant.controller;

import com.restaurant.model.Booking;
import com.restaurant.model.User;
import com.restaurant.repository.BookingRepository;
import com.restaurant.repository.TableRepository;
import com.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired private TableRepository tableRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    // Customer Home
    @GetMapping("/home")
    public String home(Authentication auth, Model model) {
        User user = userRepo.findByEmail(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("today", LocalDate.now());

        List<Booking> myBookings = bookingRepo.findByUserId(user.getId());
        long confirmed = myBookings.stream().filter(b -> "CONFIRMED".equals(b.getStatus())).count();
        long pending   = myBookings.stream().filter(b -> "PENDING".equals(b.getStatus())).count();
        model.addAttribute("confirmedCount", confirmed);
        model.addAttribute("pendingCount",   pending);
        model.addAttribute("totalBookings",  myBookings.size());
        return "index";
    }

    // Show booking form
    @GetMapping("/book")
    public String bookPage(Model model) {
        model.addAttribute("tables",  tableRepo.findByAvailable(true));
        model.addAttribute("booking", new Booking());
        return "book";
    }

    // Submit booking
    @PostMapping("/book")
    public String makeBooking(@ModelAttribute Booking booking, Authentication auth, Model model) {
        User user = userRepo.findByEmail(auth.getName());
        booking.setUser(user);

        List<Booking> conflicts = bookingRepo.findConflicting(
                booking.getTable().getId(), booking.getDate(), booking.getTime());

        if (!conflicts.isEmpty()) {
            model.addAttribute("tables",  tableRepo.findByAvailable(true));
            model.addAttribute("booking", booking);
            model.addAttribute("error",
                    "❌ This table is already booked for that date and time! Please choose another slot.");
            return "book";
        }

        booking.setStatus("PENDING");
        bookingRepo.save(booking);
        return "redirect:/mybookings?success=booked";
    }

    // My Bookings
    @GetMapping("/mybookings")
    public String myBookings(Authentication auth, Model model,
                             @RequestParam(value = "success", required = false) String success) {
        User user = userRepo.findByEmail(auth.getName());
        List<Booking> bookings = bookingRepo.findByUserId(user.getId());
        model.addAttribute("bookings", bookings);
        model.addAttribute("user",     user);
        if (success != null) model.addAttribute("success", "✅ Booking submitted! Awaiting confirmation.");
        return "mybookings";
    }

    // Booking Receipt (Customer view)
    @GetMapping("/booking/receipt/{id}")
    public String receipt(@PathVariable Long id, Authentication auth, Model model) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        User user = userRepo.findByEmail(auth.getName());
        if (booking == null || !booking.getUser().getId().equals(user.getId())) {
            return "redirect:/mybookings";
        }
        model.addAttribute("booking", booking);
        return "booking-receipt";
    }

    // Cancel a booking
    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Authentication auth) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        User user = userRepo.findByEmail(auth.getName());
        if (booking != null && booking.getUser().getId().equals(user.getId())) {
            booking.setStatus("CANCELLED");
            bookingRepo.save(booking);
        }
        return "redirect:/mybookings";
    }

    // Profile Page
    @GetMapping("/profile")
    public String profilePage(Authentication auth, Model model) {
        User user = userRepo.findByEmail(auth.getName());
        model.addAttribute("user", user);
        List<Booking> bookings = bookingRepo.findByUserId(user.getId());
        model.addAttribute("totalBookings",  bookings.size());
        model.addAttribute("confirmedCount", bookings.stream().filter(b -> "CONFIRMED".equals(b.getStatus())).count());
        model.addAttribute("pendingCount",   bookings.stream().filter(b -> "PENDING".equals(b.getStatus())).count());
        model.addAttribute("cancelledCount", bookings.stream().filter(b -> "CANCELLED".equals(b.getStatus())).count());
        model.addAttribute("recentBookings", bookings.stream().limit(3).collect(java.util.stream.Collectors.toList()));
        return "profile";
    }

    // Update Profile
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String name,
                                @RequestParam(required = false) String newPassword,
                                Authentication auth, Model model) {
        User user = userRepo.findByEmail(auth.getName());
        user.setName(name);
        if (newPassword != null && !newPassword.trim().isEmpty() && newPassword.length() >= 6) {
            user.setPassword(encoder.encode(newPassword));
        }
        userRepo.save(user);
        return "redirect:/profile?updated=true";
    }
}
