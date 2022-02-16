package by.lav.car.rental.mapper;

import by.lav.car.rental.dto.CreateUserDto;
import by.lav.car.rental.entity.UsersEntity;
import by.lav.car.rental.entity.UsersRole;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, UsersEntity> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public UsersEntity mapFrom(CreateUserDto object) {
        return UsersEntity.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(UsersRole.valueOf(object.getRole()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
