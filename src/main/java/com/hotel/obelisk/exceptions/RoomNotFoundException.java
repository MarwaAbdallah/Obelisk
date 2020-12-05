package com.hotel.obelisk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;


public class RoomNotFoundException extends ResponseStatusException {
    public RoomNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

}