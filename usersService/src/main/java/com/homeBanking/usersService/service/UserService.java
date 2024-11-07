package com.homeBanking.usersService.service;

import com.homeBanking.usersService.entities.*;
import com.homeBanking.usersService.entities.dto.UserDTO;
import com.homeBanking.usersService.entities.dto.UserRegistrationDTO;
import com.homeBanking.usersService.entities.dto.mappers.UserDtoMapper;
import com.homeBanking.usersService.exceptions.ResourceNotFoundException;
import com.homeBanking.usersService.repository.UserRepository;
import com.homeBanking.usersService.utils.AliasCvuGenerator;
import jakarta.ws.rs.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AliasCvuGenerator generator;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private final UserDtoMapper userDtoMapper;


    public UserService(UserRepository userRepository, AliasCvuGenerator generator, UserDtoMapper userDtoMapper, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.generator = generator;
        this.userDtoMapper = userDtoMapper;
        this.keycloakService = keycloakService;
    }

    public UserDTO createUser(UserRegistrationDTO userInformation) throws Exception{

        checkUserRequest(userInformation);

        Optional<User> userEmailOptional = userRepository.findByEmail(userInformation.email());
        Optional<User> userUsernameOptional = userRepository.findByUsername(userInformation.username());
        List<User> users = userRepository.findAll();
        String newCvu= "";
        String newAlias= "";
        String finalNewCvu = newCvu;
        String finalNewAlias = newAlias;


        if(userEmailOptional.isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        if(userUsernameOptional.isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        //check if cvu exists in DB and creates a new one
        do {
            newCvu = generator.generateCvu();
        } while (users.stream().anyMatch(user -> user.getCvu().equals(finalNewCvu)));

        //check if alias exists in DB and creates a new one
        do {
            newAlias= generator.generateAlias();
        } while (users.stream().anyMatch(user -> user.getAlias().equals(finalNewAlias)));

        User newUser = new User(
                userInformation.name(),
                userInformation.lastName(),
                userInformation.username(),
                userInformation.email(),
                userInformation.phoneNumber(),
                newCvu,
                newAlias,
                userInformation.password()
        );

        User userKc = keycloakService.createUser(newUser);
        newUser.setKeycloakId(userKc.getKeycloakId());

        User userSaved = userRepository.save(newUser);

        return new UserDTO(userInformation.name(), userInformation.lastName(), userInformation.username(), userInformation.email(), userInformation.phoneNumber(), newCvu, newAlias);
    }

    private void checkUserRequest(UserRegistrationDTO userInformation) throws BadRequestException {
        String phoneNumberPattern = "\\b\\d+\\b";
        String emailPattern = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
        if(userInformation.name().isEmpty()     ||
                userInformation.lastName().isEmpty() ||
                userInformation.username().isEmpty() ||
                !userInformation.email().matches(emailPattern) ||
                !userInformation.phoneNumber().matches(phoneNumberPattern) ||
                userInformation.password().isEmpty()) {
            throw new BadRequestException("Field wrong or missing");
        }
    }

    public AccessKeycloak login(Login loginData) throws Exception{
        Optional<User> optionalUser = userRepository.findByEmail(loginData.getEmail());
        if(optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }
        return keycloakService.login(loginData);
    }

    public Optional<User> findByEmail(String email) throws Exception{
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new Exception("User not found!");
        }
        return user;
    }

    public void logout(String userId) {
        keycloakService.logout(userId);
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userDtoMapper)
                .orElseThrow(()-> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    public Long getUserIdByKcId(String kcId) {
        Optional<User> userOptional = userRepository.findByKeycloakId(kcId);
        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        } else {
            return userOptional.get().getId();
        }
    }

}
