package com.toothtrek.bookings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toothtrek.bookings.entity.Timeslot;
import com.toothtrek.bookings.view.Timeslot.TimeslotDentist;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    List<Timeslot> findByOfficeId(Long officeId);

    /**
     * Checks if a timeslot is booked. Returns true if it is, false if it isn't.
     * 
     * @param timeslotId The timeslot ID to check.
     * @return boolean
     */
    @Query("SELECT CASE WHEN (EXISTS (SELECT 1 FROM Booking b WHERE b.timeslotId = :timeslotId)) THEN true ELSE false END")
    boolean isBooked(Long timeslotId);

    /**
     * Searches for timeslots based on the officeID.
     * <p>
     * Returns a list of TimeslotDentist objects.
     * <p>
     * TimeslotDentist is a view of a timeslot and its associated dentist.
     * 
     * @param officeId The office ID to search for.
     * @return List of TimeslotDentist
     * @see com.toothtrek.bookings.repository.view.TimeslotDentist
     */
    @Query("SELECT t as timeslot, d as dentist FROM Timeslot t JOIN Dentist d ON t.dentistId = d.id WHERE t.officeId = :officeId")
    List<TimeslotDentist> findTimeslotsWithDentistsByOffice(@Param("officeId") Long officeId);
}