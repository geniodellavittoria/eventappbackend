package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.models.Event;
import ch.mobro.eventapp.repositories.EventRepository;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static ch.mobro.eventapp.config.PathConstants.EVENT;
import static ch.mobro.eventapp.config.PathConstants.ID;


@Slf4j
@RequestMapping(EVENT)
@RestController
//@Controller
public class EventController {

    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    //@Autowired
    //private UserClient userClient;

    @GetMapping
    @Timed
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @GetMapping(ID)
    //@MessageMapping(RestPaths.ID)
    //@SendTo
    @Timed
    public Optional<Event> getEvent(@PathVariable("id") String id) {
        return eventRepository.findById(id);
    }

    @PostMapping
    @Timed
    public Event createEvent(@Valid @RequestBody Event event) {
        // todo: fix user feign client
        /*User user = userClient.getUser(event.getUserId());
        if (user != null) {
            event.setUser(user);
        }*/
        return eventRepository.save(event);
    }

    @PutMapping()
    @Timed
    public Event updateEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    @DeleteMapping(ID)
    @PreAuthorize("hasRole('ROLE_EVENT_ADMIN')")
    @Timed
    public void deleteEvent(@PathVariable("id") String id) {
        eventRepository.deleteById(id);
    }
}
