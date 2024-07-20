package com.yang.gatherexclusive.entity;

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
    private Long id;
    private String eventName;
    private String eventType;
    private Timestamp eventTime;
    private String eventLocation;
    private String eventDescription;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    Set<EventInvitee> eventInvitees;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    Set<EventOrganizer> eventOrganizers;

    @OneToMany(targetEntity = PotluckItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    Set<PotluckItem> eventPotluckItems;
}
