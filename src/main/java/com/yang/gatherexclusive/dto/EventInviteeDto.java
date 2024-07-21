package com.yang.gatherexclusive.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "event")
public class EventInviteeDto {
    private Long id;
    private EventDto event;
    private String inviteeEmail;
    private Boolean rsvped;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventInviteeDto that = (EventInviteeDto) o;
        return Objects.equals(id, that.id) && Objects.equals(inviteeEmail, that.inviteeEmail) && Objects.equals(rsvped, that.rsvped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inviteeEmail, rsvped);
    }
}
