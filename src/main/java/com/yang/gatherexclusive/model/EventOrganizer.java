package com.yang.gatherexclusive.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "event_organizer")
public class EventOrganizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    private boolean isCreator;
    private String description;

}
