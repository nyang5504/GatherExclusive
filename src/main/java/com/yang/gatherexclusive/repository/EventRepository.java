package com.yang.gatherexclusive.repository;

import com.yang.gatherexclusive.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEventOrganizers_Organizer_Email(String email);
    List<Event> findByEventInvitees_Invitee_Email(String email);
}
