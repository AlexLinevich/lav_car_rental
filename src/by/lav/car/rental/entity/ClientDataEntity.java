package by.lav.car.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDataEntity {
    private Integer id;
    private Integer userId;
    private String driverLicenceNo;
    private LocalDate dlExpirationDay;
    private BigDecimal creditAmount;
}
