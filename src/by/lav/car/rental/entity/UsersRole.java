package by.lav.car.rental.entity;

import java.util.Arrays;
import java.util.Optional;

public enum UsersRole {
    ADMIN,
    CLIENT;

    public static Optional<UsersRole> find(String role) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(role))
                .findFirst();
    }
}
