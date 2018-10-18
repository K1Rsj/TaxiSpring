package project.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orders", nullable = false)
    private Integer id;

    @Column(name = "departure_street", nullable = false, length = 45)
    private String departureStreet;

    @Column(name = "destination_street", nullable = false, length = 45)
    private String destinationStreet;

    @ManyToOne
    @JoinColumn(name = "cars_id", referencedColumnName = "id_cars", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id_users", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_type_id", referencedColumnName = "id_car_type", nullable = false)
    private CarType type;

    @Column(name = "price", nullable = false)
    private Long price;
}
