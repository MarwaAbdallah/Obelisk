package com.hotel.obelisk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReservationNotFoundException extends ResponseStatusException {
    public ReservationNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
