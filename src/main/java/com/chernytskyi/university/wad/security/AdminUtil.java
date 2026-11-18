package com.chernytskyi.university.wad.security;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Objects;

public class AdminUtil {
    public static Boolean isAdmin(Jwt jwt) {
        List<String> role = jwt.getClaim("roles");
        return Objects.equals(role.getFirst(), "ADMIN");
    }
}
