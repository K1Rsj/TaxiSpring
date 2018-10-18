package project.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cars", nullable = false)
    private Integer id;

    @Column(name = "model", nullable = false, length = 45)
    private String model;

    @Column(name = "number", nullable = false, length = 45, unique = true)
    private String number;

    @Column(name = "state", nullable = false, insertable = false, length = 45)
    private State state;

    @Column(name = "driver", nullable = false, length = 45)
    private String driver;

    @ManyToOne
    @JoinColumn(name = "car_type_id", referencedColumnName = "id_car_type", nullable = false)
    private CarType carType;

    public enum State {
        FREE, BUSY
    }
}
