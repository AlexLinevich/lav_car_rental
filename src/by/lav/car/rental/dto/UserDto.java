package by.lav.car.rental.dto;

import by.lav.car.rental.entity.UsersRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    UsersRole role;
}
