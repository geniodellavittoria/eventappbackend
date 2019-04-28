package ch.mobro.eventapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.Instant.now;
import static java.util.Collections.emptyList;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User implements Serializable, UserDetails {

    @Id
    private String id;
    private byte[] avatar;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private Instant dateOfBirth;
    private String address;
    private String city;
    private String zipCode;
    private String phone;
    private String mobile;
    private String country;
    private List<Role> roles = new ArrayList<>();

    @NotNull
    private List<PasswordHistory> passwordHistories;
    private String refreshToken;
    private String accessToken;
    private Instant lastLoginTime;
    private Instant expirationDate;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        Optional<PasswordHistory> passwordHistory = passwordHistories.stream().max(Comparator.comparing(PasswordHistory::getTimestamp));
        return passwordHistory.map(PasswordHistory::getPasswordHash).orElse(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        if (expirationDate != null) {
            return expirationDate.isAfter(now());
        }
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

