package com.yang.gatherexclusive.dto;

import lombok.Data;

@Data
public class EventInviteeDto {
    private Long id;
    private EventDto event;
    private String inviteeEmail;
    private Boolean rsvped;
}
