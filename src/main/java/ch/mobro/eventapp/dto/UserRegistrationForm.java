package ch.mobro.eventapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationForm {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private Instant dateOfBirth;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String zipCode;
    private String phone;
    private String mobile;
    private String country;

    @NotNull
    private String password;
}
