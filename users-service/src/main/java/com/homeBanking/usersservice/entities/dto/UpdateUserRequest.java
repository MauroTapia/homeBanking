package com.homeBanking.usersservice.entities.dto;

public record UpdateUserRequest (String name,
                                 String lastName,
                                 String username,
                                 String email,
                                 String phoneNumber) {
}
