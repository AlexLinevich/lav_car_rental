package by.lav.car.rental.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
