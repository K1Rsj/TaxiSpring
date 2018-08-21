package project.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.dao.repository.CarRepository;
import project.model.dao.repository.CarTypeRepository;
import project.model.dao.repository.OrderRepository;
import project.model.dao.repository.UserRepository;
import project.model.domain.Car;
import project.model.domain.CarType;
import project.model.domain.Order;
import project.model.domain.User;
import project.model.exception.NoFreeCarWithSuchTypeException;
import project.model.util.OrderPriceGenerator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;
    private CarRepository carRepository;
    private UserRepository userRepository;
    private CarTypeRepository carTypeRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CarRepository carRepository, UserRepository userRepository, CarTypeRepository carTypeRepository) {
        this.orderRepository = orderRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.carTypeRepository = carTypeRepository;
    }

    public Order makeOrder(String login, String departureStreet, String destinationStreet, String type) throws NoFreeCarWithSuchTypeException {
        Optional<Car> car = carRepository.findByStateAndCarTypeType(Car.State.FREE, type);
        if (car.isPresent()) {
            carRepository.updateCarState(Car.State.BUSY, car.get().getId());
            User user = userRepository.findByLogin(login);
            CarType carType = carTypeRepository.findCarTypeByType(type);

            Long orderPrice = OrderPriceGenerator.getOrderPrice(user.getMoneySpent(), departureStreet, destinationStreet, carType);
            return Order.builder()
                    .departureStreet(departureStreet)
                    .destinationStreet(destinationStreet)
                    .car(car.get())
                    .user(user)
                    .price(orderPrice)
                    .type(carType)
                    .build();

        } else {
            throw new NoFreeCarWithSuchTypeException();
        }
    }

    public void confirmOrder(Order order) {
        orderRepository.save(order);
        Long orderPriceWithMoneySpent = order.getUser().getMoneySpent() + order.getPrice();
        userRepository.updateMoneySpent(order.getUser().getId(), orderPriceWithMoneySpent);
        carRepository.updateCarState(Car.State.FREE, order.getCar().getId());
    }

    public void cancelOrder(Order order) {
        carRepository.updateCarState(Car.State.FREE, order.getCar().getId());
    }

    public List<Order> getAllUserOrders(String login) {
        return orderRepository.findByUserLogin(login);
    }
}
