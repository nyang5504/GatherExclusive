package com.yang.gatherexclusive.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String eventName;
    private String eventType;
    private Timestamp eventTime;
    private String eventLocation;
    private String eventDescription;

    @OneToMany(mappedBy = "event")
    Set<EventInvitee> eventInvitees;

    @OneToMany(mappedBy = "event")
    Set<EventOrganizer> eventOrganizers;

    @OneToMany(targetEntity = Potluck.class, cascade = CascadeType.ALL)
    Set<Potluck> eventPotlucks;
}
