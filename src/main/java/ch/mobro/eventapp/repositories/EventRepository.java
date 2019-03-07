package ch.mobro.eventapp.repositories;


import ch.mobro.eventapp.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {

}
