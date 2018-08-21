package project.model.domain.converter;

import project.model.domain.Car;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CarStateConverter implements AttributeConverter<Car.State, String> {
    @Override
    public String convertToDatabaseColumn(Car.State state) {
        return state.name();
    }

    @Override
    public Car.State convertToEntityAttribute(String s) {
        return Car.State.valueOf(s);
    }
}
