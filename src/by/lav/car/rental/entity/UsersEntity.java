package by.lav.car.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UsersRole role;
}
