package by.lav.car.rental.service;

import by.lav.car.rental.dao.UsersDao;
import by.lav.car.rental.dto.CreateUserDto;
import by.lav.car.rental.exception.ValidationException;
import by.lav.car.rental.mapper.CreateUserMapper;
import by.lav.car.rental.validator.CreateUserValidator;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UsersDao usersDao = UsersDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    public Integer create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var usersEntity = createUserMapper.mapFrom(userDto);
        usersDao.save(usersEntity);
        return usersEntity.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
