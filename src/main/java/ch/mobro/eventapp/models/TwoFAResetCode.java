package ch.mobro.eventapp.models;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TwoFAResetCode {

    private Integer code;
}
