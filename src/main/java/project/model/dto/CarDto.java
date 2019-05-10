package project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.model.domain.Car;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Integer id;
    private String model;
    private String number;
    private Car.State state;
    private String driver;
    private String carType;
}
