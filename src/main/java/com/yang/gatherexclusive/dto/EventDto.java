package com.yang.gatherexclusive.dto;

import com.yang.gatherexclusive.entity.EventInvitee;
import com.yang.gatherexclusive.entity.PotluckItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "potluckItems")
public class EventDto {
    private Long id;
    @NotEmpty
    private String eventName;
    @NotEmpty
    private String eventType;
    @NotNull
    private LocalDateTime eventTime;
    @NotEmpty
    private String eventLocation;
    @NotEmpty
    private String eventDescription;
    @NotEmpty
    private String invites;
    private List<PotluckItemDto> potluckItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDto eventDto = (EventDto) o;
        return Objects.equals(id, eventDto.id) && Objects.equals(eventName, eventDto.eventName) && Objects.equals(eventType, eventDto.eventType) && Objects.equals(eventTime, eventDto.eventTime) && Objects.equals(eventLocation, eventDto.eventLocation) && Objects.equals(eventDescription, eventDto.eventDescription) && Objects.equals(invites, eventDto.invites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, eventType, eventTime, eventLocation, eventDescription, invites);
    }
}
