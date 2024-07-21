package com.yang.gatherexclusive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"eventInvitees", "eventOrganizers", "eventPotluckItems"})
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    private String eventType;
    private LocalDateTime eventTime;
    private String eventLocation;
    private String eventDescription;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    Set<EventInvitee> eventInvitees;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    Set<EventOrganizer> eventOrganizers;

    @OneToMany(targetEntity = PotluckItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    Set<PotluckItem> eventPotluckItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(eventName, event.eventName) && Objects.equals(eventType, event.eventType) && Objects.equals(eventTime, event.eventTime) && Objects.equals(eventLocation, event.eventLocation) && Objects.equals(eventDescription, event.eventDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, eventType, eventTime, eventLocation, eventDescription);
    }
}
