package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.dto.*;
import ch.mobro.eventapp.models.PasswordHistory;
import ch.mobro.eventapp.models.TwoFAResetCode;
import ch.mobro.eventapp.models.User;
import ch.mobro.eventapp.repositories.UserRepository;
import ch.mobro.eventapp.util.TimeBasedOneTimePasswordUtil;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static ch.mobro.eventapp.config.PathConstants.*;
import static java.time.Instant.now;

@Slf4j
@RestController
@RequestMapping(USER)
public class UserController {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    //@Override
    @GetMapping(ID)
    public Optional<User> getUser(@PathVariable String id) {
        return repository.findById(id);
    }

    //@Override
    @GetMapping
    public List<User> getUsers() {
        return repository.findAll();
    }

    /*
    @GetMapping("/roles" + ID)
    public List<User> getUsersForRole(@PathVariable String id) {
        return repository.findAll().filter(u -> u.getRoles().contains(id));
    }
*/
    //@GetMapping(ID)
    /*public Mono<User> getUser(@PathVariable String id) {
        return repository.findById(id);
    }*/
    @PostMapping
    @Timed
    public User createUser(@Valid @RequestBody User user) {
        // todo encode password
        return repository.save(user);
    }

    @PutMapping(ID)
    @Timed
    public User updateUser(@Valid @RequestBody User user) {
        return repository.save(user);
    }

    @DeleteMapping(ID)
    @Timed
    public void deleteUser(@PathVariable String id) {
        repository.deleteById(id);
    }

    @PostMapping(ID)
    @Timed
    public User changePassword(@PathVariable("id") String id, @Valid @RequestBody ChangePasswordForm changePasswordForm) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent() || !changePasswordForm.getPassword().equals(changePasswordForm.getConfirmPassword())) {
            return null;
        }
        PasswordHistory passwordHistory = new PasswordHistory(now(), passwordEncoder.encode(changePasswordForm.getPassword()));
        List<PasswordHistory> passwordHistories = user.get().getPasswordHistories();
        //passwordHistories.stream().
        passwordHistories.add(passwordHistory);
        repository.save(user.get());
        return user.get();
    }

    @GetMapping(ID + USER_CREATE_TWO_FA_SECRET)
    @Timed
    public TwoFASecretGenerationForm generateTwoFA(@PathVariable("id") String id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return null;
        }
        String secret = TimeBasedOneTimePasswordUtil.generateBase32Secret();
        user.get().setTwoFASecret(secret);
        user.get().setTwoFAEnabled(false);
        repository.save(user.get());

        return new TwoFASecretGenerationForm(secret);
    }

    @PostMapping(ID + USER_VERIFY_TWO_FA_SECRET)
    @Timed
    public TwoFAOneTimePasswordResponseForm verifyTwoFASecret(@PathVariable("id") String id, @RequestBody TwoFASecretVerificationForm twoFASecretVerificationForm) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return new TwoFAOneTimePasswordResponseForm(false);
        }
        try {
            boolean isValid = TimeBasedOneTimePasswordUtil.validateCurrentNumber(twoFASecretVerificationForm.getSecret(), twoFASecretVerificationForm.getOtp(),
                    60000);
            if (isValid) {
                user.get().setTwoFAEnabled(true);
                repository.save(user.get());
            }
            return new TwoFAOneTimePasswordResponseForm(isValid);
        } catch (GeneralSecurityException e) {
            log.error("Error occured during verify second factor  secret", e);
            return new TwoFAOneTimePasswordResponseForm(false);
        }
    }

    @DeleteMapping(ID + USER_RESET_TWO_FA_SECRET)
    @Timed
    public void removeTwoFA(@PathVariable("id") String id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            user.get().getTwoFAResetCodes().clear();
            user.get().setTwoFAEnabled(false);
            user.get().setTwoFASecret(null);
            repository.save(user.get());
        }
    }

    @PostMapping(ID + USER_VERIFY_TWO_FA_OTP)
    @Timed
    public TwoFAOneTimePasswordResponseForm verifyTwoFAOneTimePassword(@PathVariable("id") String id,
                                              @RequestBody TwoFAOneTimePasswordRequestForm twoFAOneTimePasswordRequestForm) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent() || !user.get().getTwoFAEnabled()) {
            return new TwoFAOneTimePasswordResponseForm(false);
        }
        try {
            boolean isValid = TimeBasedOneTimePasswordUtil.validateCurrentNumber(user.get().getTwoFASecret(),
                    twoFAOneTimePasswordRequestForm.getOtp(), 60000);
            user.get().setLastLoginTime(now());
            repository.save(user.get());
            return new TwoFAOneTimePasswordResponseForm(isValid);
        } catch (GeneralSecurityException e) {
            log.error("Error occured during verify OTP", e);
            return new TwoFAOneTimePasswordResponseForm(false);
        }
    }

    @GetMapping(ID + USER_RESET_TWO_FA_CODES)
    @Timed
    public TwoFAResetCodeGenerationForm regenerateTwoFAResetCodes(@PathVariable("id") String id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return null;
        }
        user.get().getTwoFAResetCodes().clear();
        generateTwoFAResetCodes(user.get());
        repository.save(user.get());
        return new TwoFAResetCodeGenerationForm(user.get().getTwoFAResetCodes());
    }

    private void generateTwoFAResetCodes(User user) {
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            user.getTwoFAResetCodes().add(new TwoFAResetCode(100000 + rnd.nextInt(900000)));
        }
    }

    @GetMapping(ID + USER_TWO_FA_CODES)
    @Timed
    public List<TwoFAResetCode> getTwoFAResetCodes(@PathVariable("id") String id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return null;
        }
        return user.get().getTwoFAResetCodes();
    }

    @PostMapping(ID + USER_RESET_TWO_FA_CODES)
    @Timed
    public Boolean verifyTwoFAResetCode(@PathVariable("id") String id, @RequestBody TwoFAResetForm twoFAResetForm) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            return false;
        }
        return user.get().getTwoFAResetCodes().removeIf(c -> c.getCode().equals(twoFAResetForm.getResetCode()));
    }

    /*
    @PutMapping
    public Mono<User> updateUser(@RequestBody User user) {
        return repository.save(user);
    }

    @DeleteMapping(ID)
    public Mono<Void> deleteUser(@PathVariable String id) {
        return repository.deleteById(id);
    }
    */
}
