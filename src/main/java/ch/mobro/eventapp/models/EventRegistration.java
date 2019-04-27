package ch.mobro.eventapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class EventRegistration implements Serializable {

    private EventRegistrationCategory eventRegistrationCategory;

    //private Instant timestamp;

    private User user;

    private Double paidPrice;

}
