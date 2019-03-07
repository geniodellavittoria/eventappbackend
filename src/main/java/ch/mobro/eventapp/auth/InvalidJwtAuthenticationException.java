package ch.mobro.eventapp.auth;

import javax.naming.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}

