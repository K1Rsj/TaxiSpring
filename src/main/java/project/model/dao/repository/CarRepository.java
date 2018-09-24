package project.model.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.model.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findFirstByStateAndCarTypeType(Car.State carState, String type);

    List<Car> findAllByOrderByCarTypeId();

    @Modifying
    @Query("UPDATE Car car SET car.state = :carState WHERE car.id = :carId")
    void updateCarState(@Param("carState") Car.State carState, @Param("carId") Integer carId);
}
