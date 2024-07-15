package com.yang.gatherexclusive.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;

//    @OneToOne(cascade = CascadeType.ALL)
//    private EventOrganizer organizer;
//    @ManyToMany(targetEntity = Event.class, cascade = CascadeType.ALL)
//    @JoinTable(name = "event_invitee", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
//    private Set<Event> eventsInvited;

    @OneToMany(mappedBy = "invitee")
    Set<EventInvitee> eventInvitees;

    @OneToMany(mappedBy = "organizer")
    Set<EventOrganizer> eventOrganizers;

//    @ManyToMany(targetEntity = Event.class, cascade = CascadeType.ALL)
//    @JoinTable(name = "event_organizer", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
//    private Set<Event> eventsOrganized;
}
