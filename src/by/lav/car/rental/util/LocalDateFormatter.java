package by.lav.car.rental.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@UtilityClass
public class LocalDateFormatter {

    private static final String PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public LocalDate format(String data) {
        return LocalDate.parse(data, FORMATTER);
    }

    public boolean isValid(String data) {
        try {
            return Optional.ofNullable(data)
                    .map(LocalDateFormatter::format)
                    .isPresent();
        } catch (DateTimeParseException exception) {
            return false;
        }
    }
}
