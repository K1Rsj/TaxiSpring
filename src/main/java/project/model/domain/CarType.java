package project.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "car_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_car_type", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @Column(name = "starting_price", nullable = false)
    private Integer startingPrice;

    @Column(name = "price_per_km", nullable = false)
    private Integer pricePerKilometer;

    @Column(name = "discount", nullable = false)
    private Integer discount;
}
