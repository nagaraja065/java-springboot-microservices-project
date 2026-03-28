package com.restaurant.controller;

import com.restaurant.model.Booking;
import com.restaurant.model.RestaurantTable;
import com.restaurant.repository.BookingRepository;
import com.restaurant.repository.TableRepository;
import com.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private TableRepository tableRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UserRepository userRepo;

    // Admin Dashboard with chart data
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalBookings   = bookingRepo.count();
        long pendingCount    = bookingRepo.countByStatus("PENDING");
        long confirmedCount  = bookingRepo.countByStatus("CONFIRMED");
        long cancelledCount  = bookingRepo.countByStatus("CANCELLED");
        long rejectedCount   = bookingRepo.countByStatus("REJECTED");
        long totalTables     = tableRepo.count();
        long totalCustomers  = userRepo.findAll().stream()
                .filter(u -> "CUSTOMER".equals(u.getRole())).count();

        model.addAttribute("totalBookings",  totalBookings);
        model.addAttribute("pendingCount",   pendingCount);
        model.addAttribute("confirmedCount", confirmedCount);
        model.addAttribute("cancelledCount", cancelledCount);
        model.addAttribute("rejectedCount",  rejectedCount);
        model.addAttribute("totalTables",    totalTables);
        model.addAttribute("totalCustomers", totalCustomers);

        List<Booking> todayBookings = bookingRepo.findByDateOrderByTimeAsc(LocalDate.now());
        model.addAttribute("todayBookings", todayBookings);
        model.addAttribute("todayCount", todayBookings.size());

        // Chart: bookings for next 7 days
        List<String> chartLabels = new ArrayList<>();
        List<Long>   chartData   = new ArrayList<>();
        long maxBar = 1L;
        for (int i = 0; i < 7; i++) {
            LocalDate day = LocalDate.now().plusDays(i);
            chartLabels.add(day.getDayOfWeek().toString().substring(0, 3)
                    + " " + day.getDayOfMonth());
            long count = bookingRepo.findByDateOrderByTimeAsc(day).size();
            chartData.add(count);
            if (count > maxBar) maxBar = count;
        }
        model.addAttribute("chartLabels", chartLabels);
        model.addAttribute("chartData",   chartData);
        model.addAttribute("chartMax",    maxBar);

        return "admin-dashboard";
    }

    // All Bookings with Search & Filter
    @GetMapping("/bookings")
    public String allBookings(@RequestParam(value = "search", required = false) String search,
                              @RequestParam(value = "status", required = false) String status,
                              @RequestParam(value = "date",   required = false)
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                              Model model) {
        List<Booking> bookings = bookingRepo.findAll();

        if (status != null && !status.isEmpty()) {
            bookings = bookings.stream()
                    .filter(b -> b.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }
        if (date != null) {
            bookings = bookings.stream()
                    .filter(b -> b.getDate().equals(date))
                    .collect(Collectors.toList());
        }
        if (search != null && !search.trim().isEmpty()) {
            String keyword = search.toLowerCase();
            bookings = bookings.stream()
                    .filter(b -> b.getUser().getName().toLowerCase().contains(keyword)
                              || b.getUser().getEmail().toLowerCase().contains(keyword)
                              || b.getTable().getTableNumber().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
        }

        model.addAttribute("bookings",    bookings);
        model.addAttribute("search",      search);
        model.addAttribute("status",      status);
        model.addAttribute("date",        date);
        model.addAttribute("totalCount",  bookings.size());
        return "all-bookings";
    }

    // Approve Booking
    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        bookingRepo.findById(id).ifPresent(b -> { b.setStatus("CONFIRMED"); bookingRepo.save(b); });
        return "redirect:/admin/bookings";
    }

    // Reject Booking
    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        bookingRepo.findById(id).ifPresent(b -> { b.setStatus("REJECTED"); bookingRepo.save(b); });
        return "redirect:/admin/bookings";
    }

    // Booking Receipt / Detail
    @GetMapping("/booking/{id}")
    public String bookingDetail(@PathVariable Long id, Model model) {
        bookingRepo.findById(id).ifPresent(b -> model.addAttribute("booking", b));
        return "booking-receipt";
    }

    // Manage Tables
    @GetMapping("/tables")
    public String manageTables(Model model) {
        model.addAttribute("tables",   tableRepo.findAll());
        model.addAttribute("newTable", new RestaurantTable());
        return "manage-tables";
    }

    @PostMapping("/tables/add")
    public String addTable(@ModelAttribute("newTable") RestaurantTable table) {
        table.setAvailable(true);
        tableRepo.save(table);
        return "redirect:/admin/tables";
    }

    @PostMapping("/tables/toggle/{id}")
    public String toggleTable(@PathVariable Long id) {
        tableRepo.findById(id).ifPresent(t -> { t.setAvailable(!t.isAvailable()); tableRepo.save(t); });
        return "redirect:/admin/tables";
    }

    @PostMapping("/tables/delete/{id}")
    public String deleteTable(@PathVariable Long id) {
        tableRepo.deleteById(id);
        return "redirect:/admin/tables";
    }
}
