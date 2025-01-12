package com.ffa.back.repositories;

import com.ffa.back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirebaseUuid(String firebaseUuid);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.language WHERE u.email = :email")
    Optional<User> findByEmailWithLanguage(String email);
}
