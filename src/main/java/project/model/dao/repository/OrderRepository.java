package project.model.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.domain.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserLogin(String login);
}
