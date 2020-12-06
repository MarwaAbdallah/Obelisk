package com.hotel.obelisk.controller;

import com.hotel.obelisk.model.Reservation;
import com.hotel.obelisk.model.Room;
import com.hotel.obelisk.model.User;
import com.hotel.obelisk.repository.ReservationRepository;
import com.hotel.obelisk.repository.RoomRepository;
import com.hotel.obelisk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class ViewController {
    @Autowired
    private final ReservationRepository resRepo;
    private final RoomRepository roomRepo;
    private final UserRepository userRepo;

    public ViewController(ReservationRepository resRepo,
                                    RoomRepository roomRepo,
                                    UserRepository userRepo) {
        this.resRepo = resRepo;
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/admin/users")
    public ModelAndView showAllUsers() {
        List<User> users =  userRepo.findAll();
        Map<String, Object> params = new HashMap<>();
        params.put("users",users);
        return new ModelAndView("users", params);
    }
    @GetMapping("/rooms")
    public ModelAndView showAllRooms() {
        List<Room> rooms =  roomRepo.findAll();
        Map<String, Object> params = new HashMap<>();
        params.put("rooms",rooms);
        return new ModelAndView("rooms", params);
    }
    @GetMapping("/reservations")
    public ModelAndView showAllReservations() {
        List<Reservation> reservations =  resRepo.findAll();
        Map<String, Object> params = new HashMap<>();
        params.put("reservations",reservations);
        return new ModelAndView("reservations", params);
    }
}
