package com.yang.gatherexclusive.service.impl;

import com.yang.gatherexclusive.dto.EventDto;
import com.yang.gatherexclusive.dto.EventInviteeDto;
import com.yang.gatherexclusive.dto.PotluckItemDto;
import com.yang.gatherexclusive.entity.*;
import com.yang.gatherexclusive.repository.EventRepository;
import com.yang.gatherexclusive.repository.UserRepository;
import com.yang.gatherexclusive.service.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    /**
     * saves an event to the database
     * @param eventDto
     */
    @Override
    public void saveEvent(EventDto eventDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        Event event = new Event();
        event.setEventName(eventDto.getEventName());
        event.setEventType(eventDto.getEventType());
        event.setEventTime(eventDto.getEventTime());
        event.setEventLocation(eventDto.getEventLocation());
        event.setEventDescription(eventDto.getEventDescription());
//        Set<EventInviteeDto> invites = new HashSet<>(eventDto.getInvites());
        Set<EventInvitee> inviteeSet = Arrays.stream(eventDto.getInvites().split(","))
                .map(untrimmed -> untrimmed.trim())
                .map(email -> eventInviteeFromEmail(event,email))
                .collect(Collectors.toSet());
        if(!(inviteeSet.size() == 1 && inviteeSet.contains(null))) {
            event.setEventInvitees(inviteeSet);
        }
        else{
            event.setEventInvitees(new HashSet<>());
        }
        inviteeSet.forEach(invitee -> System.out.println("Invitee in set: " + invitee));

        Set<EventOrganizer> organizerSet = new HashSet<>();
        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvent(event);
        organizer.setOrganizer(userRepository.findByEmail(currentUserEmail));
        organizer.setIsCreator(true);
        organizerSet.add(organizer);
        event.setEventOrganizers(organizerSet);

        if(!eventDto.getPotluckItems().isEmpty()){
            Set<PotluckItem> items = eventDto.getPotluckItems().stream().map(itemDto ->
                    convertDtoToEntity(itemDto)).collect(Collectors.toSet());
            event.setEventPotluckItems(items);
        }
        else{
            event.setEventPotluckItems(new HashSet<>());
        }
        eventRepository.save(event);
    }

    /**
     * find EventDto through an event id
     * @param id
     * @return EventDto that has a certain id
     */
    @Override
    public EventDto findEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()) {
            return convertEntityToDto(event.get());
        }
        return null;
    }

    /**
     *
     * @param email
     * @return a list of EventDto for a user
     */
    @Override
    public List<EventDto> findEventsByOrganizerEmail(String email) {
        List<Event> events = eventRepository.findByEventOrganizers_Organizer_Email(email);
        return events.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    /**
     * 
     * @param email
     * @return
     */
    @Override
    public List<EventDto> findPendingEventsByInviteeEmail(String email) {
        List<Event> events = eventRepository.findByEventInvitees_Invitee_EmailAndEventInvitees_Rsvped(email, false);
        return events.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> findRSVPedEventsByInviteeEmail(String email) {
        List<Event> events = eventRepository.findByEventInvitees_Invitee_EmailAndEventInvitees_Rsvped(email, true);
        return events.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            eventRepository.delete(event.get());
        }
    }

//    private EventInvitee convertDtoToEntity(EventInviteeDto eventInviteeDto, Event event){
//        EventInvitee eventInvitee = new EventInvitee();
//        User inviteeUser = userRepository.findByEmail(eventInviteeDto.getInviteeEmail());
//        eventInvitee.setEvent(event);
//        eventInvitee.setInvitee(inviteeUser);
//        eventInvitee.setRsvped(false);
//        return eventInvitee;
//    }

    /**
     * helper function to convert PotluckItemDto to PotluckItem
     * @param potluckItemDto
     * @return PotluckItem
     */
    private PotluckItem convertDtoToEntity(PotluckItemDto potluckItemDto){
        PotluckItem potluckItem = new PotluckItem();
        potluckItem.setItemName(potluckItemDto.getItemName());
        potluckItem.setQuantity(potluckItemDto.getQuantity());
        return potluckItem;
    }

    /**
     * helper function to convert PotluckItem to PotluckItemDto
     * @param potluckItem
     * @return PotluckItemDto
     */
    private PotluckItemDto convertEntityToDto(PotluckItem potluckItem){
        PotluckItemDto potluckItemDto = new PotluckItemDto();
        potluckItemDto.setItemName(potluckItem.getItemName());
        potluckItemDto.setQuantity(potluckItem.getQuantity());
        return potluckItemDto;
    }

    /**
     * helper function to find EventInvitee from event object and email of user
     * @param event
     * @param email
     * @return EventInvitee
     */
    private EventInvitee eventInviteeFromEmail(Event event, String email){
        EventInvitee eventInvitee = new EventInvitee();
        User invitee = userRepository.findByEmail(email);
        if(invitee == null){
            return null;
        }
        eventInvitee.setEvent(event);
        eventInvitee.setInvitee(invitee);
        eventInvitee.setRsvped(false);
        return eventInvitee;
    }

    /**
     * helper function to convert Event to EventDto
     * @param event
     * @return EventDto
     */
    private EventDto convertEntityToDto(Event event){
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setEventName(event.getEventName());
        eventDto.setEventType(event.getEventType());
        eventDto.setEventTime(event.getEventTime());
        eventDto.setEventLocation(event.getEventLocation());
        eventDto.setEventDescription(event.getEventDescription());
        Set<String> inviteeEmails = event.getEventInvitees().stream().map(eInvitee -> eInvitee.getInvitee().getEmail()).collect(Collectors.toSet());
        eventDto.setInvites(String.join(", ", inviteeEmails));
        List<PotluckItem> potluckItems = event.getEventPotluckItems().stream().toList();
        eventDto.setPotluckItems(potluckItems.stream().map(item -> convertEntityToDto(item)).collect(Collectors.toList()));
        return eventDto;
    }
}
