package ch.mobro.eventapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Role implements Serializable {

    private String name;

}
