package by.lav.car.rental.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersEntity {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UsersRole role;
}
