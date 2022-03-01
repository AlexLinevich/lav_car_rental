package by.lav.car.rental.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateClientDataDto {
    String userId;
    String driverLicenceNo;
    String dlExpirationDay;
    String creditAmount;
}
