package org.example.s30050tpo06.entities;

import java.time.LocalDate;

public record Person(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String address,
        String university,
        String country,
        String email,
        String phoneNumber,
        String gender,
        String job,
        String hobbies
) {
}