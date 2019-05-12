package ch.mobro.eventapp.dto;

import ch.mobro.eventapp.models.EventRegistrationCategory;
import ch.mobro.eventapp.models.User;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class EventRegistrationForm {

    private EventRegistrationCategory eventRegistrationCategory;

    private String username;

    private Double paidPrice;
}
