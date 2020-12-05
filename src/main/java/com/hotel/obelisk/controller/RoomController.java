package com.hotel.obelisk.controller;

import com.hotel.obelisk.exceptions.RoomNotFoundException;
import com.hotel.obelisk.model.Room;
import com.hotel.obelisk.repository.ReservationRepository;
import com.hotel.obelisk.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private final RoomRepository roomRepo;
    public RoomController(ReservationRepository resRepo,
                          RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    @GetMapping
    public Page<Room> getAllRooms(@RequestParam Optional<Integer> page,
                                  @RequestParam Optional<String> booked){
        Pageable pageable = PageRequest.of(page.orElse(0),10, Sort.by("roomNo").ascending());
        String bk = booked.orElse("all");
        Page<Room> rooms;
        if(bk.equals("booked")){
            rooms = roomRepo.findAllBooked(pageable);
        } else if (bk.equals("available")) {
            rooms = roomRepo.findAllNonBooked(pageable);
        }else {
            rooms = roomRepo.findAll(pageable);
        }
        return rooms;
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable long id) {
        Optional<Room> room = roomRepo.findById(id);
        if (room.isEmpty()) {
            String reason = "Room "+id+" not found";
            throw new RoomNotFoundException(HttpStatus.NOT_FOUND,
                    String.format("No Room found for id (%s)", id));
        }
        return room.get();
    }

}
