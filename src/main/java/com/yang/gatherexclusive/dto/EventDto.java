package com.yang.gatherexclusive.dto;

import com.yang.gatherexclusive.entity.EventInvitee;
import com.yang.gatherexclusive.entity.PotluckItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
public class EventDto {
    private Long id;
    @NotEmpty
    private String eventName;
    @NotEmpty
    private String eventType;
    @NotNull
    private Timestamp eventTime;
    @NotEmpty
    private String eventLocation;
    @NotEmpty
    private String eventDescription;
    @NotEmpty
    private List<EventInviteeDto> invites;
    private List<PotluckItemDto> potluckItems;
}
