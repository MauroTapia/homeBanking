package com.homeBanking.usersservice.entities.dto;

public record UserDTO(
        String name,
        String lastName,
        String username,
        String email,
        String phoneNumber,
        String cvu,
        String alias
) {
}
