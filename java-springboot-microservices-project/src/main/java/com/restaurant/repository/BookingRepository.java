package com.restaurant.repository;

import com.restaurant.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    long countByStatus(String status);

    List<Booking> findByDateOrderByTimeAsc(LocalDate date);

    List<Booking> findByStatus(String status);

    @Query("SELECT b FROM Booking b WHERE b.table.id = :tableId " +
           "AND b.date = :date AND b.time = :time " +
           "AND b.status IN ('PENDING', 'CONFIRMED')")
    List<Booking> findConflicting(@Param("tableId") Long tableId,
                                  @Param("date")    LocalDate date,
                                  @Param("time")    LocalTime time);
}
