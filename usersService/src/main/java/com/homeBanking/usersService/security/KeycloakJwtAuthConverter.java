package com.homeBanking.usersService.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

public class KeycloakJwtAuthConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String ROLES_CLAIM = "roles";
    private static final String RESOURCE_ACCESS_CLAIM = "resource_access";
    private static final String USERS_CLAIM = "users";
    private static final String SCOPES_CLAIM = "scope";
    private static final String GROUPS_CLAIM = "group";


    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        return List.of();
    }
}
