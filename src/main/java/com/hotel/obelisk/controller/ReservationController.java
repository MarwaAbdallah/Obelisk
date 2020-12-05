package com.hotel.obelisk.controller;

import com.hotel.obelisk.exceptions.ReservationNotFoundException;
import com.hotel.obelisk.exceptions.UserNotFoundException;
import com.hotel.obelisk.model.Reservation;
import com.hotel.obelisk.model.Role;
import com.hotel.obelisk.model.Room;
import com.hotel.obelisk.model.User;
import com.hotel.obelisk.repository.ReservationRepository;
import com.hotel.obelisk.repository.RoomRepository;
import com.hotel.obelisk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private final ReservationRepository resRepo;
    private final RoomRepository roomRepo;
    private final UserRepository userRepo;

    public ReservationController(ReservationRepository resRepo,
                                 RoomRepository roomRepo,
                                 UserRepository userRepo) {
        this.resRepo = resRepo;
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    Page<Reservation> getAllReservations(Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                10, Sort.by("resId").ascending());

        Page<Reservation> res = resRepo.findAll(pageable);
        if (res.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Reservation in the System");
        }
        return res;
    }
    @GetMapping("/{id}")
    Reservation getReservationById(@PathVariable long id) {
        Optional<Reservation> reservation =
                resRepo.findById(id);
        if (reservation.isEmpty()) {
            throw new ReservationNotFoundException(HttpStatus.BAD_REQUEST,
                    String.format("No Reservation found for id (%s)", id));
        }
        return reservation.get();
    }
    @PostMapping
    ResponseEntity<Reservation> createReservation(@RequestBody Room r,
                                                  @RequestParam long userId,
                                                  @RequestParam String fromDate,
                                                  @RequestParam String toDate,
                                                  @RequestParam String customerEmail) {
        // = (is not present in reservation table) for a date range including this reservation
        // Also, verify LocalDate from < to
        // verify room is prese

        Optional<User> usr = userRepo.findById(userId);
        if (usr.isEmpty()) {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND,
                    String.format("No RUser found for id (%s)", userId));
        }
        User user = usr.get();
        Optional<Room> rm = roomRepo.findById(r.getRoomNo());
        LocalDate fromD = LocalDate.parse(fromDate);
        LocalDate toD = LocalDate.parse(toDate);
        if(rm.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The Room (%s) does Not Exist. " +
                            "Please choose an existing room", r.getRoomNo()));
        } else {
            Room room = rm.get();
            if (room.isBooked()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("The Room (%s) is Booked. " +
                                "Please book an available room", room.getRoomNo()));
            }
            Reservation reservation = new Reservation(fromD, toD,
                    user, room, customerEmail);
            resRepo.save(reservation);
            return ResponseEntity.ok(reservation);
        }
    }


}
