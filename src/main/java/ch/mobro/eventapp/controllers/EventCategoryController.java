package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.config.Variables;
import ch.mobro.eventapp.models.Event;
import ch.mobro.eventapp.models.EventCategory;
import ch.mobro.eventapp.repositories.EventRepository;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static ch.mobro.eventapp.config.PathConstants.*;
import static java.util.Collections.emptyList;

@RequestMapping(EVENT + "/" + ID + EVENT_CATEGORY)
@RestController
public class EventCategoryController {

    private final EventRepository repository;

    @Autowired
    public EventCategoryController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Timed
    public List<EventCategory> getEventCategories(@PathVariable("id") String id) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return emptyList();
        }
        return event.get().getCategories();
    }

    @PostMapping
    @Timed
    public Event addEventCategory(@PathVariable("id") String id, @RequestBody EventCategory eventCategory) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        event.get().getCategories().add(eventCategory);
        repository.save(event.get());
        return event.get();
    }

    @DeleteMapping(CATEGORY_ID)
    @Timed
    public Event removeEventCategory(@PathVariable("id") String id, @PathVariable(Variables.CATEGORY_ID) String categoryId) {
        Optional<Event> event = repository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        event.get().getCategories().removeIf(c -> c.getId().equals(categoryId));
        repository.save(event.get());
        return event.get();
    }
}
