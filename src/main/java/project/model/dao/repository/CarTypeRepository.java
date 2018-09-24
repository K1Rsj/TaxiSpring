package project.model.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.model.domain.CarType;

public interface CarTypeRepository extends JpaRepository<CarType, Integer> {
    @Modifying
    @Query("UPDATE CarType carType SET carType.discount = :discount WHERE carType.type = :type")
    void updateDiscount(@Param("discount") Integer discount, @Param("type") String type);

    CarType findByType(String type);
}
