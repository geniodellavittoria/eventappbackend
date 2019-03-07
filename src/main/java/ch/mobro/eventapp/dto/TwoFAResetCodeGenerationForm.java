package ch.mobro.eventapp.dto;

import ch.mobro.eventapp.models.TwoFAResetCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TwoFAResetCodeGenerationForm {
    private List<TwoFAResetCode> resetCodes;
}
