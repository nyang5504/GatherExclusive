package com.yang.gatherexclusive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"eventInvitees", "eventOrganizers"})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email);
    }
}
