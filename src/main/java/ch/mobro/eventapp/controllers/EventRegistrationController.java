package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.config.Variables;
import ch.mobro.eventapp.dto.EventRegistrationForm;
import ch.mobro.eventapp.models.Event;
import ch.mobro.eventapp.models.EventRegistration;
import ch.mobro.eventapp.models.User;
import ch.mobro.eventapp.repositories.EventRepository;
import ch.mobro.eventapp.repositories.UserRepository;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static ch.mobro.eventapp.config.PathConstants.*;
import static java.time.Instant.now;
import static java.util.Collections.emptyList;

@RequestMapping(EVENT + "/" + ID + EVENT_REGISTRATION)
@RestController
public class EventRegistrationController {

    private final EventRepository repository;

    private final UserRepository userRepository;

    @Autowired
    public EventRegistrationController(EventRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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
    public Event createEventRegistration(@PathVariable("id") String id, @RequestBody EventRegistrationForm eventRegistrationForm) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        Optional<User> user = userRepository.findByUsername(eventRegistrationForm.getUsername());
        if (user.isPresent()) {
            EventRegistration eventRegistration = EventRegistration.builder()
                    .eventRegistrationCategory(eventRegistrationForm.getEventRegistrationCategory())
                    .paidPrice(eventRegistrationForm.getPaidPrice())
                    .timestamp(now())
                    .user(user.get())
                    .build();
            event.get().getEventRegistrations().add(eventRegistration);
            Event updatedEvent = repository.save(event.get());
            updatedEvent.calculateUsedPlace();
            return updatedEvent;
        }
        return event.get();
    }

    @DeleteMapping(USER_ID)
    @Timed
    public Event deleteEventRegistration(@PathVariable("id") String id,
                                         @PathVariable(Variables.USER_ID) String username) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        event.get().getEventRegistrations().removeIf(r -> r.getUser() != null && r.getUser().getUsername().equals(username));
        Event updatedEvent = repository.save(event.get());
        updatedEvent.calculateUsedPlace();
        return updatedEvent;
    }
}
