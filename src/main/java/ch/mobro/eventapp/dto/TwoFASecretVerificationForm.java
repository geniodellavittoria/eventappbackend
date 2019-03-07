package ch.mobro.eventapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TwoFASecretVerificationForm {

    private String secret;

    private Integer otp;
}
