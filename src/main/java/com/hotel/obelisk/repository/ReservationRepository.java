package com.hotel.obelisk.repository;

import com.hotel.obelisk.model.Reservation;
import com.hotel.obelisk.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Page<Reservation> findAll(Pageable pageable);
    List<Reservation> findAll();
}
