package ch.mobro.eventapp.controllers;

import ch.mobro.eventapp.auth.JwtTokenProvider;
import ch.mobro.eventapp.dto.JwtTokenResponse;
import ch.mobro.eventapp.dto.UserCredentials;
import ch.mobro.eventapp.dto.UserRegistrationForm;
import ch.mobro.eventapp.models.PasswordHistory;
import ch.mobro.eventapp.models.Role;
import ch.mobro.eventapp.models.User;
import ch.mobro.eventapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static ch.mobro.eventapp.config.PathConstants.AUTH;
import static ch.mobro.eventapp.config.PathConstants.AUTH_LOGIN;
import static java.time.Instant.now;
import static java.util.Collections.singletonList;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping(AUTH)
@Slf4j
public class AuthController {

    private final UserRepository repository;

    final
    AuthenticationManager authenticationManager;

    final
    JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserCredentials userCredentials) {
        log.info("Authenticate user");

        try {
            String username = userCredentials.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userCredentials.getPassword()));
            String token = jwtTokenProvider.createToken(username, repository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
                    .getRoles()
                    .stream()
                    .map(Role::getName)
                    .collect(Collectors.toList()));
            return ok(new JwtTokenResponse(username, token));
        } //catch (AuthenticationException e) {
        catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }

    }

    //@HystrixCommand(fallbackMethod = "authTimedOut")
    @PostMapping(AUTH_LOGIN)
    public ResponseEntity<?> authenticate(@Valid @RequestBody UserCredentials userCredentials) {
        log.info("Authenticate user");
        /*Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCredentials.getUsername(),
                        userCredentials.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
*/
        //String jwt = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegistrationForm userRegistrationForm) {
        User user = User.builder()
                .username(userRegistrationForm.getUsername())
                .email(userRegistrationForm.getEmail())
                .name(userRegistrationForm.getName())
                .surname(userRegistrationForm.getSurname())
                .dateOfBirth(userRegistrationForm.getDateOfBirth())
                .address(userRegistrationForm.getAddress())
                .city(userRegistrationForm.getCity())
                .zipCode(userRegistrationForm.getZipCode())
                .phone(userRegistrationForm.getPhone())
                .mobile(userRegistrationForm.getMobile())
                .country(userRegistrationForm.getCountry())
                .passwordHistories(singletonList(new PasswordHistory(now(),
                        passwordEncoder.encode(userRegistrationForm.getPassword()))))
                .build();
        repository.save(user);
    }

    public String authTimedOut() {
        return "Authentication timed out!";
    }


}
