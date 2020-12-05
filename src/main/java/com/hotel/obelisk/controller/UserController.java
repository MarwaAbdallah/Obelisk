package com.hotel.obelisk.controller;

import com.hotel.obelisk.exceptions.RoomNotFoundException;
import com.hotel.obelisk.exceptions.UserNotFoundException;
import com.hotel.obelisk.model.Room;
import com.hotel.obelisk.repository.UserRepository;
import com.hotel.obelisk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class UserController {
    /** IMPORTANT !! If class has > 1 Constructor, must add @Autowired
     above the method to indicate when a parameter represents
     a Spring bean to be autowired/injected.
     **/
    @Autowired
    private final UserRepository userRepo;
    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/users") // Thanks to Jackson dependencies in spring boot dev, return to JSON for ex
    Page<User> getAllUsers(@RequestParam Optional<Integer> page,
                               @RequestParam Optional<String> enabled) {
        Pageable paging = PageRequest.of(page.orElse(0), 10, Sort.by("userId").ascending());
        Page<User> users;
        String en = enabled.orElse("default");
        if (en.equals("true")) {
            users = userRepo.findAllEnabledUsers(paging);
        } else if (en.equals("false")) {
            users = userRepo.findAllDisabledUsers(paging);
        } else {
            users = userRepo.findAll(paging);
            //users = userRepo.findAllDisabledUsers(paging);
        }
        return users;
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    User getUserbyId(@PathVariable long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            String reason = "User "+id+" not found";
            throw new UserNotFoundException(HttpStatus.NOT_FOUND,
                    String.format("No Room found for id (%s)", id));
        }
        return user.get();
    }


    @PostMapping
    User postUser(@RequestBody User user) {
        return userRepo.save(user);
    }
    @PutMapping("/users/{id}")
    ResponseEntity<User> putUser(@PathVariable long id,
            @RequestBody User user) {
        return (userRepo.existsById(id))
                ? new ResponseEntity<>(userRepo.save(user),HttpStatus.OK)
                : new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
    }
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable long id) {
        userRepo.deleteById(id);
    }
}
