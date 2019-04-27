package ch.mobro.eventapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class EventInvitation {

    private String eventId;

    private String email;
}
