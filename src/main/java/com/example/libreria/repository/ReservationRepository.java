package com.example.libreria.repository;

import com.example.libreria.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByIdAndStatus(Long id, Reservation.ReservationStatus status);


    List<Reservation> findByUserIdAndStatus(Long userId, Reservation.ReservationStatus status);


    List<Reservation> findByBookExternalIdAndStatus(Long bookId, Reservation.ReservationStatus status);


    @Query("""
        SELECT r FROM Reservation r 
        WHERE r.user.id = :userId 
        AND r.book.externalId = :bookId 
        AND r.status = 'ACTIVE'
    """)
    Optional<Reservation> findActiveReservationForUserAndBook(
            @Param("userId") Long userId,
            @Param("bookId") Long bookId
    );


    @Query("""
        SELECT r FROM Reservation r
        WHERE r.status = 'ACTIVE'
        AND r.expectedReturnDate < :today
    """)
    List<Reservation> findOverdueReservations(@Param("today") LocalDate today);


    List<Reservation> findByStatus(Reservation.ReservationStatus status);
}

