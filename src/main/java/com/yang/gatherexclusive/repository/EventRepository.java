package com.yang.gatherexclusive.repository;

import com.yang.gatherexclusive.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 <p>
 *     JPA repository for Event entity model
 * </p>
 * <p>
 *     Provides methods for CRUD operations
 * </p>
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * Finds all events that a user organizes based on the {@code email} of the user
     * @param email email of an organizer
     * @return {@code List<Event>}
     */
    List<Event> findByEventOrganizers_Organizer_Email(String email);

    /**
     * Finds all events that a user has rsvp'd to based on the {@code email} of the user
     * @param email
     * @param rsvped
     * @return {@code List<Event>}
     */
    List<Event> findByEventInvitees_Invitee_EmailAndEventInvitees_Rsvped(String email, Boolean rsvped);

    void deleteEventById(Long id);
}
