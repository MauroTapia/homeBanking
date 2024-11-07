package com.homeBanking.usersService.entities.dto.mappers;

import com.homeBanking.usersService.entities.User;
import com.homeBanking.usersService.entities.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDTO>{

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCvu(),
                user.getAlias()
        );
    }
}