package com.hotel.obelisk.repository;

import com.hotel.obelisk.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RoomRepository extends CrudRepository<Room, Long> {

    /** UI **/
   List<Room> findAll();
    @Query("SELECT rm FROM Room rm where rm.isBooked = true")
    List<Room> findAllBooked();
    @Query("SELECT rm FROM Room rm where rm.isBooked = false")
    List<Room> findAllNonBooked();

    /** API **/
    Page<Room> findAll(Pageable pageable);
    @Query("SELECT rm FROM Room rm where rm.isBooked = true")
    Page<Room> findAllBooked(Pageable pageable);
    @Query("SELECT rm FROM Room rm where rm.isBooked = false")
    Page<Room> findAllNonBooked(Pageable pageable);
}
