package com.yang.gatherexclusive.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "event_invitee")
public class EventInvitee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "invitee_id")
    private User invitee;

    private boolean rsvped;
}
