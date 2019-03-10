package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.config.Variables;
import ch.mobro.eventapp.models.Event;
import ch.mobro.eventapp.models.EventRegistration;
import ch.mobro.eventapp.repositories.EventRepository;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static ch.mobro.eventapp.config.PathConstants.*;
import static ch.mobro.eventapp.config.Variables.EVENT_REGISTRATION_ID;
import static java.util.Collections.emptyList;

@RequestMapping(EVENT + "/" + ID + EVENT_REGISTRATION)
@RestController
public class EventRegistrationController {

    private final EventRepository repository;

    @Autowired
    public EventRegistrationController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Timed
    public List<EventRegistration> getEventRegistrations(@PathVariable("id") String id) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return emptyList();
        }
        return event.get().getEventRegistrations();
    }

    @PostMapping
    @Timed
    public Event createEventRegistration(@PathVariable("id") String id, @RequestBody EventRegistration eventRegistration) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        event.get().getEventRegistrations().add(eventRegistration);
        repository.save(event.get());
        return event.get();
    }

    @DeleteMapping(REGISTRATION_ID)
    @Timed
    public Event deleteEventRegistration(@PathVariable("id") String id,
                                         @PathVariable(Variables.EVENT_REGISTRATION_ID) String eventRegistrationdId) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        event.get().getEventRegistrations().removeIf(r -> r.getId().equals(eventRegistrationdId));
        repository.save(event.get());
        return event.get();
    }
}
