package com.example.EmailService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idemails;

    private String email;

    public Email() {}

    public Email(String email) {
        this.email = email;

    }

    public Long getId() {
        return idemails;
    }

    public void setId(Long idemails) {
        this.idemails = idemails;
    }

    public String getEmailAddress() {
        return email;
    }

    public void setEmailAddress(String email) {
        this.email = email;
    }

}

