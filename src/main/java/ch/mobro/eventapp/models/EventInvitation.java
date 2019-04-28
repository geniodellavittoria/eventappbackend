package ch.mobro.eventapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventInvitation {

    @NonNull
    private String email;
}
