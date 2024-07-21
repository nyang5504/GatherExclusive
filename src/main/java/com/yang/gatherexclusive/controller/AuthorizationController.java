package com.yang.gatherexclusive.controller;

import com.yang.gatherexclusive.dto.EventDto;
import com.yang.gatherexclusive.dto.UserDto;
import com.yang.gatherexclusive.entity.Event;
import com.yang.gatherexclusive.entity.User;
import com.yang.gatherexclusive.service.EventService;
import com.yang.gatherexclusive.service.UserService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthorizationController {

    private UserService userService;
    private EventService eventService;

    @Autowired
    public AuthorizationController( UserService userService, EventService eventService ) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

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

    @GetMapping("/create")
    public String createEvent(Model model) {
        EventDto eventDto = new EventDto();
//        eventDto.setPotluckItems(new ArrayList<>());
        model.addAttribute("event", eventDto);
        return "create_event";
    }

    @PostMapping("/newEvent/save")
    public String saveEvent(@Valid @ModelAttribute("event") EventDto eventDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "create_event";
        }
        eventService.saveEvent(eventDto);
        return "redirect:/index";
    }

    @GetMapping("/dashboard")
    public String displayDashboard(Model model, Principal principal) {
        List<EventDto> invitedEvents = eventService.findEventsByInviteeEmail(principal.getName());
        model.addAttribute("invitedEvents", invitedEvents);
        List<EventDto> organizedEvents = eventService.findEventsByOrganizerEmail(principal.getName());
        model.addAttribute("organizedEvents", organizedEvents);
        return "dashboard";
    }
}
