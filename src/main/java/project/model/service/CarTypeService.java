package project.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.dao.repository.CarTypeRepository;
import project.model.domain.CarType;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarTypeService {

    private CarTypeRepository carTypeRepository;

    @Autowired
    public CarTypeService(CarTypeRepository carTypeRepository) {
        this.carTypeRepository = carTypeRepository;
    }

    public void addCarType(CarType carType) {
        carTypeRepository.save(carType);
    }

    public void deleteCarTypeById(Integer id) {
        carTypeRepository.deleteById(id);
    }

    public void updateCarType(CarType carType) {
        addCarType(carType);
    }

    public void updateDiscount(String type, Integer discount) {
        carTypeRepository.updateDiscount(discount, type);
    }

    public List<CarType> getAllCarTypes() {
        return carTypeRepository.findAll();
    }

    public Optional<CarType> getCarTypeById(Integer id) {
        return carTypeRepository.findById(id);
    }
}