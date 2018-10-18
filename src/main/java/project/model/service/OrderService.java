package project.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.dao.repository.CarRepository;
import project.model.dao.repository.CarTypeRepository;
import project.model.dao.repository.OrderRepository;
import project.model.dao.repository.UserRepository;
import project.model.domain.Car;
import project.model.domain.CarType;
import project.model.domain.Order;
import project.model.domain.User;
import project.model.exception.NoFreeCarWithSuchTypeException;
import project.model.exception.NoStreetWithSuchName;
import project.model.util.GeoCodingUtils;
import project.model.util.OrderPriceGenerator;

import java.io.IOException;
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

    public Order makeOrder(String login, String departureStreet, String destinationStreet, String type)
            throws NoFreeCarWithSuchTypeException, IOException, NoStreetWithSuchName {
        GeoCodingUtils.checkForStreetNamesExistence(departureStreet, destinationStreet);
        Optional<Car> car = carRepository.findFirstByStateAndCarTypeType(Car.State.FREE, type);
        if (car.isPresent()) {
            carRepository.updateCarState(Car.State.BUSY, car.get().getId());
            User user = userRepository.findByLogin(login).get();
            CarType carType = carTypeRepository.findByType(type);
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

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void removeOrderById(Integer id) {
        orderRepository.deleteById(id);
    }
}
