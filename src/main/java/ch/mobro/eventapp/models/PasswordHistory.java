package ch.mobro.eventapp.models;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordHistory implements Serializable {

    private Instant timestamp;

    private String passwordHash;

}
