package project.model.service;

import static project.constant.EntityFields.NUMBER;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.model.dao.repository.CarRepository;
import project.model.dao.repository.CarTypeRepository;
import project.model.domain.Car;
import project.model.domain.CarType;
import project.model.domain.Order;
import project.model.dto.CarDto;
import project.model.exception.EntityAlreadyExists;

@Service
@Transactional
public class CarService {

    private CarRepository carRepository;
    private CarTypeRepository carTypeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CarService(CarRepository carRepository, CarTypeRepository carTypeRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.carTypeRepository = carTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(rollbackFor = EntityAlreadyExists.class)
    public void addCar(CarDto carDTO) throws EntityAlreadyExists{
        try {
            CarType carType = carTypeRepository.findByType(carDTO.getCarType());
            Car car = modelMapper.map(carDTO, Car.class);
            car.setCarType(carType);
            carRepository.save(car);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExists(NUMBER, carDTO.getNumber());
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
