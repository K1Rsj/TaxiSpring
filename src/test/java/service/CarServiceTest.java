package service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import project.model.dao.repository.CarRepository;
import project.model.dao.repository.CarTypeRepository;
import project.model.domain.Car;
import project.model.domain.CarType;
import project.model.dto.CarDto;
import project.model.exception.EntityAlreadyExists;
import project.model.service.CarService;

public class CarServiceTest {

    @InjectMocks
    private CarService carService;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarTypeRepository carTypeRepository;
    @Mock
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<Car> carArgumentCaptor;
    private CarType carType;
    private CarDto carDto;
    private Car car;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        carType = new CarType();
        carDto = new CarDto();
        car = new Car();
        car.setCarType(carType);
    }

    @Test
    public void addCarTest() throws EntityAlreadyExists {
        when(carTypeRepository.findByType(any())).thenReturn(carType);
        when(modelMapper.map(carDto, Car.class)).thenReturn(car);

        carService.addCar(carDto);

        verify(carRepository).save(carArgumentCaptor.capture());
        verify(carTypeRepository).findByType(any());
        verify(modelMapper).map(carDto, Car.class);
        Car savedCar = carArgumentCaptor.getValue();

        assertThat(savedCar, is(car));
        assertThat(car.getCarType(), is(carType));
    }

    @Test(expected = EntityAlreadyExists.class)
    public void addCarThrowsEntityAlreadyExist() throws EntityAlreadyExists {
        when(carTypeRepository.findByType(any())).thenReturn(carType);
        when(modelMapper.map(carDto, Car.class)).thenReturn(car);
        when(carRepository.save(car)).thenThrow(new DataIntegrityViolationException("duplicate"));

        carService.addCar(carDto);
    }

    @Test
    public void getAllCarsTest() {
        when(carRepository.findAllByOrderByCarTypeId()).thenReturn(Collections.emptyList());

        List<Car> actual = carService.getAllCars();

        assertThat(actual, is(Collections.emptyList()));
    }

    @Test
    public void getCarByIdTest() {
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        Car expected = Car.builder().carType(new CarType()).build();

        Car actual = carService.getCarById(100500).get();

        assertThat(actual, is(expected));
    }
}
