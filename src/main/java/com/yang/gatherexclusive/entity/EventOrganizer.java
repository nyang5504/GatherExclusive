package com.yang.gatherexclusive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"event", "organizer"})
@Entity
@Table(name = "event_organizer")
public class EventOrganizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    private Boolean isCreator;
//    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventOrganizer that = (EventOrganizer) o;
        return Objects.equals(id, that.id) && Objects.equals(isCreator, that.isCreator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isCreator);
    }
}
