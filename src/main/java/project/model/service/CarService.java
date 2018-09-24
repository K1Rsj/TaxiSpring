package project.model.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.dao.repository.CarRepository;
import project.model.dao.repository.CarTypeRepository;
import project.model.domain.Car;
import project.model.domain.Order;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarService {

    private CarRepository carRepository;
    private CarTypeRepository carTypeRepository;

    @Autowired
    public CarService(CarRepository carRepository, CarTypeRepository carTypeRepository) {
        this.carRepository = carRepository;
        this.carTypeRepository = carTypeRepository;
    }


    public void addCar(Car car, String type) throws ConstraintViolationException {
        try {
            car.setCarType(carTypeRepository.findByType(type));
            carRepository.save(car);
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getMessage(), e.getSQLException(), e.getConstraintName());
        }
    }

    public List<Car> getAllCars() {
        return carRepository.findAllByOrderByCarTypeId();
    }

    public Optional<Car> getCarById(Integer id) {
        return carRepository.findById(id);
    }

    public void deleteCarById(Integer id) {
        carRepository.deleteById(id);
    }

    public void updateCar(Car car) {
        carRepository.save(car);
    }

    public void changeCarStateFromBusyToFree(Order order) {
        carRepository.updateCarState(Car.State.BUSY, order.getCar().getId());
    }
}
