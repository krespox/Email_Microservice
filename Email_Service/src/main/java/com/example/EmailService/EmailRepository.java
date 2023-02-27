package com.example.EmailService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query("SELECT e.email FROM emails e")
    List<String> findEmailAddresses();


}
