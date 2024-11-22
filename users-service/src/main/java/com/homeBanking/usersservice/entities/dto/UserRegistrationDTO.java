package com.homeBanking.usersservice.entities.dto;

public record UserRegistrationDTO(
        String name,
        String lastName,
        String username,
        String email,
        String phoneNumber,
        String password
) {
}
