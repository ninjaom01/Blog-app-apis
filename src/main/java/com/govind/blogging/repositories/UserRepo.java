package com.govind.blogging.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.govind.blogging.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}

//package com.govind.blogging.repositories;
//import com.govind.blogging.entities.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.Optional;
//
//public interface UserRepo extends JpaRepository<User, Integer> {
//    Optional<User> findByUsername(String username);
//    boolean existsByUsername(String username);
//    boolean existsByEmail(String email);
//}