package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.config.Variables;
import ch.mobro.eventapp.models.Event;
import ch.mobro.eventapp.models.EventInvitation;
import ch.mobro.eventapp.repositories.EventRepository;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

import static ch.mobro.eventapp.config.PathConstants.*;
import static java.util.Collections.emptyList;

@RequestMapping(EVENT + "/" +  ID + EVENT_INVITATION)
@RestController
public class EventInvitationController {

    private final EventRepository repository;

    @Autowired
    public EventInvitationController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Timed
    public List<EventInvitation> getEventInvitations(@PathVariable("id") String id) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return emptyList();
        }
        return event.get().getEventInvitations();
    }

    @PostMapping
    @Timed
    public Event createEventInvitation(@PathVariable("id") String id, @RequestBody EventInvitation eventInvitation) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        event.get().getEventInvitations().add(eventInvitation);
        repository.save(event.get());
        return event.get();
    }

    @DeleteMapping(EMAIL_ID)
    @Timed
    public Event deleteEventInvitation(@PathVariable("id") String id,
                                       @PathVariable(Variables.EMAIL_ID) String email) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        event.get().getEventInvitations().removeIf(i -> i.getEmail().equals(email));
        repository.save(event.get());
        return event.get();
    }
}
