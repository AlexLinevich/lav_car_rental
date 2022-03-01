package by.lav.car.rental.service;

import by.lav.car.rental.dao.ClientDataDao;
import by.lav.car.rental.dto.CreateClientDataDto;
import by.lav.car.rental.mapper.CreateClientDataMapper;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ClientDataService {

    private static final ClientDataService INSTANCE = new ClientDataService();

    private final ClientDataDao clientDataDao = ClientDataDao.getInstance();
    private final CreateClientDataMapper createClientDataMapper = CreateClientDataMapper.getInstance();

    public void create(CreateClientDataDto clientDataDto) {
        var clientDataEntity = createClientDataMapper.mapFrom(clientDataDto);
        clientDataDao.save(clientDataEntity);
    }

    public Optional<Integer> findClientDataId(Integer userId) {
        return clientDataDao.findIdByUserId(userId);
    }

    public static ClientDataService getInstance() {
        return INSTANCE;
    }
}
