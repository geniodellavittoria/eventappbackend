package ch.mobro.eventapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class EventRegistrationCategory {

    @Id
    private String id;

    private String Category;
}
