package ch.mobro.eventapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Event implements Serializable {

    @Id
    private String id;

    private User organizer;

    private String name;

    private Instant creationTime;

    private Instant startTime;

    private Instant endTime;

    private Integer place;

    private Double price;

    private String description;

    private String eventImage;

    private double longitude;

    private double latitude;

    private boolean privateEvent;

    private List<EventInvitation> eventInvitations = new ArrayList<>();

    private List<EventRegistration> eventRegistrations = new ArrayList<>();

    private List<EventRegistration> guestList = new ArrayList<>();

    @NotNull
    private List<EventCategory> categories = new ArrayList<>();


}
