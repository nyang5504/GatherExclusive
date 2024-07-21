package com.yang.gatherexclusive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"event", "invitee"})
@Entity
@Table(name = "event_invitee")
public class EventInvitee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invitee_id")
    private User invitee;

    private Boolean rsvped;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventInvitee that = (EventInvitee) o;
        return Objects.equals(id, that.id) && Objects.equals(event.getId(), that.event.getId()) && Objects.equals(invitee.getId(), that.invitee.getId()) && Objects.equals(rsvped, that.rsvped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rsvped);
    }
}
