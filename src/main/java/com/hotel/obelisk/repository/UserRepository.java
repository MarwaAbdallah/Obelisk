package com.hotel.obelisk.repository;

import com.hotel.obelisk.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    /** FOR UI **/
    List<User> findAll();
    @Query("SELECT u FROM User u WHERE u.enabled=true")
    List<User> findAllEnabledUsers();
    @Query("SELECT u FROM User u WHERE u.enabled=false")
    List<User> findAllDisabledUsers();

    /** FOR API **/
    Page<User> findAll(Pageable pageable);
    @Query("SELECT u FROM User u WHERE u.enabled=true")
    Page<User> findAllEnabledUsers(Pageable pageable); // pass a sorting method opbject when calling

    @Query("SELECT u FROM User u WHERE u.enabled=false")
    Page<User> findAllDisabledUsers(Pageable pageable);

}
