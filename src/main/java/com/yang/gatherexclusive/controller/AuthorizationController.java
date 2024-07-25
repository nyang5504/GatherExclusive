package com.yang.gatherexclusive.controller;

import com.yang.gatherexclusive.dto.EventDto;
import com.yang.gatherexclusive.dto.EventInviteeDto;
import com.yang.gatherexclusive.dto.UserDto;
import com.yang.gatherexclusive.entity.Event;
import com.yang.gatherexclusive.entity.User;
import com.yang.gatherexclusive.service.EventInviteeService;
import com.yang.gatherexclusive.service.EventService;
import com.yang.gatherexclusive.service.UserService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class  AuthorizationController {

    private UserService userService;
    private EventService eventService;
    private EventInviteeService eventInviteeService;

    @Autowired
    public AuthorizationController( UserService userService, EventService eventService, EventInviteeService eventInviteeService ) {
        this.userService = userService;
        this.eventService = eventService;
        this.eventInviteeService = eventInviteeService;
    }

    /**
     * @return index html page
     */
    @GetMapping("index")
    public String home() {
        return "index";
    }

    /**
     * @return login html page
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     *
     * @param model user
     * @return register html page
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    /**
     *
     * @param userDto
     * @param result
     * @param model
     * @return register page if validation fails. Otherwise, redirect to /register?success
     */
    @PostMapping("register/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", "email.exists", "There is already a user registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    /**
     *
     * @param model
     * @return create_event html page
     */
    @GetMapping("/create")
    public String createEvent(Model model) {
        EventDto eventDto = new EventDto();
//        eventDto.setPotluckItems(new ArrayList<>());
        model.addAttribute("event", eventDto);
        return "create_event";
    }

    /**
     *
     * @param eventDto
     * @param result
     * @param model
     * @return create_event html page if validation fails, otherwise redirect to the index page
     */
    @PostMapping("/newEvent/save")
    public String saveEvent(@Valid @ModelAttribute("event") EventDto eventDto, BindingResult result, Model model) {

        try{
            if (result.hasErrors()) {
                model.addAttribute("event", eventDto);
                return "create_event";
            }
            eventService.saveEvent(eventDto);
        } catch (Exception e){
            System.out.println("Error saving event");
            model.addAttribute("event", eventDto);
            return "redirect:/create";
        }
        return "redirect:/dashboard";
    }

    /**
     *
     * @param model
     * @param principal
     * @return dashboard html page
     */
    @GetMapping("/dashboard")
    public String displayDashboard(Model model, Principal principal) {
        List<EventDto> invitedEvents = eventService.findPendingEventsByInviteeEmail(principal.getName());
        model.addAttribute("invitedEvents", invitedEvents);
        List<EventDto> attendingEvents = eventService.findRSVPedEventsByInviteeEmail(principal.getName());
        model.addAttribute("attendingEvents", attendingEvents);
        List<EventDto> organizedEvents = eventService.findEventsByOrganizerEmail(principal.getName());
        model.addAttribute("organizedEvents", organizedEvents);
        return "dashboard";
    }

    /**
     * @param id
     * @param model
     * @param principal
     * @return dashboard html page
     */
    @PostMapping("/dashboard/rsvp/{id}")
    public String rsvp(@PathVariable("id") Long id, Model model, Principal principal) {
        eventInviteeService.rsvpToEvent(id, principal.getName());
        return "redirect:/dashboard";
    }

    /**
     *
     * @param id
     * @param model
     * @param principal
     * @return event_details html page
     */
    @GetMapping("/dashboard/viewEvent/{id}")
    public String viewEvent(@PathVariable("id") Long id, Model model, Principal principal) {
        EventDto eventDto = eventService.findEventById(id);
        model.addAttribute("event", eventDto);
        return "event_details";
    }

    /**
     *
     * @param id
     * @param model
     * @param principal
     * @return organizer_event_details html page
     */
    @GetMapping("/dashboard/organizer/viewEvent/{id}")
    public String organizerViewEvent(@PathVariable("id") Long id, Model model, Principal principal) {
        EventDto eventDto = eventService.findEventById(id);
        model.addAttribute("event", eventDto);
        List<EventInviteeDto> eventInvitees = eventInviteeService.findInvitedByEventId(id);
        model.addAttribute("eventInvitees", eventInvitees);
        List<EventInviteeDto> eventInviteesRsvped = eventInviteeService.findRsvpedByEventId(id);
        model.addAttribute("eventInviteesRsvped", eventInviteesRsvped);
        return "organizer_event_details";
    }

    @PostMapping("/dashboard/deleteEvent/{id}")
    public String deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return "redirect:/dashboard";
    }
}
