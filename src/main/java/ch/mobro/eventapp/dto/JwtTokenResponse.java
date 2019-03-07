package ch.mobro.eventapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenResponse {

    @NotNull
    private String username;

    @NotNull
    private String token;
}
