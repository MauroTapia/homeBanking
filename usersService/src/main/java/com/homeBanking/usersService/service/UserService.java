package com.homeBanking.usersService.service;

import com.homeBanking.usersService.config.KeycloakClientConfig;
import com.homeBanking.usersService.repository.UserRepository;
import com.homeBanking.usersService.utils.AliasCvuGenerator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AliasCvuGenerator generator;

    @Autowired
    private KeycloakService keycloakService;



}
