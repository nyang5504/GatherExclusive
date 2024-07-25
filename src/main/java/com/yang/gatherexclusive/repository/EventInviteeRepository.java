package com.yang.gatherexclusive.repository;

import com.yang.gatherexclusive.entity.Event;
import com.yang.gatherexclusive.entity.EventInvitee;
import com.yang.gatherexclusive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *     JPA repository for EventInvitee entity model
 * </p>
 * <p>
 *     Provides methods for CRUD operations
 * </p>
 */
@Repository
public interface EventInviteeRepository extends JpaRepository<EventInvitee, Long> {
    /**
     * Finds an existing invitation to an event for the parameters {@code event} and {@code user}
     * @param event event the invite pertains to
     * @param user user that received an invitation
     * @return {@code EventInvitee} that has both attributes
     */
    EventInvitee findByEventAndInvitee(Event event, User user);

    /**
     * Finds all existing invitations to an event for the parameters {@code eventId} and {@code rsvped}
     * @param eventId id of the event the invitation pertains to
     * @param rsvped response to the invitation
     * @return {@code List<EventInvitee>} that has both attributes
     */
    List<EventInvitee> findByEvent_IdAndRsvped(Long eventId, boolean rsvped);
}
