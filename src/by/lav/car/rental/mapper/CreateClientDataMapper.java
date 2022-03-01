package by.lav.car.rental.mapper;

import by.lav.car.rental.dto.CreateClientDataDto;
import by.lav.car.rental.entity.ClientDataEntity;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateClientDataMapper implements Mapper<CreateClientDataDto, ClientDataEntity> {

    private static final CreateClientDataMapper INSTANCE = new CreateClientDataMapper();

    @Override
    public ClientDataEntity mapFrom(CreateClientDataDto object) {
        return ClientDataEntity.builder()
                .userId(Integer.valueOf(object.getUserId()))
                .driverLicenceNo(object.getDriverLicenceNo())
                .dlExpirationDay(LocalDate.parse(object.getDlExpirationDay()))
                .creditAmount(BigDecimal.valueOf(Double.parseDouble(object.getCreditAmount())))
                .build();
    }

    public static CreateClientDataMapper getInstance() {
        return INSTANCE;
    }
}
