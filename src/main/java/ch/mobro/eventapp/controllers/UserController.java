package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.dto.*;
import ch.mobro.eventapp.models.PasswordHistory;
import ch.mobro.eventapp.models.User;
import ch.mobro.eventapp.repositories.UserRepository;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(USERNAME)
    public User getUser(@PathVariable("username") String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @GetMapping
    public List<User> getUsers() {
        return repository.findAll();
    }

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

    @DeleteMapping(USERNAME)
    @Timed
    public void deleteUser(@PathVariable("username") String id) {
        repository.deleteByUsername(id);
    }

    @PostMapping(USERNAME)
    @Timed
    public User changePassword(@PathVariable("username") String username, @Valid @RequestBody ChangePasswordForm changePasswordForm) {
        Optional<User> user = repository.findByUsername(username);
        if (!user.isPresent() || !changePasswordForm.getPassword().equals(changePasswordForm.getConfirmPassword())) {
            return null;
        }
        PasswordHistory passwordHistory = new PasswordHistory(now(), passwordEncoder.encode(changePasswordForm.getPassword()));
        List<PasswordHistory> passwordHistories = user.get().getPasswordHistories();
        passwordHistories.add(passwordHistory);
        repository.save(user.get());
        return user.get();
    }


}
