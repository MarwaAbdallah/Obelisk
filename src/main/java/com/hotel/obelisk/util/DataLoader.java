package com.hotel.obelisk.util;

import com.hotel.obelisk.model.Authorities;
import com.hotel.obelisk.model.Reservation;
import com.hotel.obelisk.model.Room;
import com.hotel.obelisk.repository.ReservationRepository;
import com.hotel.obelisk.repository.AuthorityRepository;
import com.hotel.obelisk.repository.RoomRepository;
import com.hotel.obelisk.repository.UserRepository;
import com.hotel.obelisk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader {
    @Autowired
    private final UserRepository userRepo;
    @Autowired
    private final AuthorityRepository roleRepo;
    @Autowired
    private final ReservationRepository resRepo;
    @Autowired
    private final RoomRepository roomRepo;
    public DataLoader(UserRepository userRepo, AuthorityRepository roleRepo,
                      ReservationRepository resRepo, RoomRepository roomRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.resRepo = resRepo;
        this.roomRepo = roomRepo;
    }

    @PostConstruct
    private void loadData() {
        Authorities adm = new Authorities("Administrator", "admin");
        Authorities emp =new Authorities("Employee", "Joelle@obelisk.com");
        Authorities rescres = new Authorities("ReservationCreator",
                "servaccount@hbooking.com");
        Random r = new Random();
        double roomPriceMin = 50.0;
        double roomPriceMax = 350.0;
        List <Room> rooms = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            double price = roomPriceMin + (roomPriceMax - roomPriceMin) * r.nextDouble();
            price = Math.round(price * 100.0) / 100.0;
            Room room = new Room(false,price);
            rooms.add(room);
        }
        roomRepo.saveAll(rooms);
        roleRepo.saveAll(List.of(
                adm,
                emp,
                rescres
        ));
        User bob = new User("Bob@obelisk.com", "password", 1);
        User admin = new User("admin", "password",1);
        User alice = new User("Alice@admin.com", "password",1);
        User joelle = new User("Joelle@obelisk.com", "password",1);
        User srvacc = new User("servaccount@hbooking.com", "password",1);
        User steve = new User("steve@obelisk.com", "password",0);
        User don = new User("don@obelisk.com.com", "password",0);
        userRepo.saveAll(List.of(
                bob, admin, alice, joelle, srvacc, steve, don
        ));
        resRepo.saveAll(List.of(
                new Reservation(LocalDate.of(2021,01,01),
                        LocalDate.of(2021,02,16),
                        alice, rooms.get(52),"keynan@outlook.com"),

                new Reservation(LocalDate.of(2020,12,29),
                        LocalDate.of(2021,01,10),
                        joelle, rooms.get(203),"james@gmail.com"),

                new Reservation(LocalDate.of(2021,01,30),
                        LocalDate.of(2021,02,9),
                        srvacc, rooms.get(406),"john.user@gmail.com"),

                new Reservation(LocalDate.of(2021,01,15),
                        LocalDate.of(2021,01,30),
                        srvacc, rooms.get(980),"ericandlaura@gmail.com"),

                new Reservation(LocalDate.of(2021,03,20),
                        LocalDate.of(2021,04,15),
                        srvacc, rooms.get(40),"marie@outlook.com")

        ));

    }
}
