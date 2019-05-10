package project.controller;

import static project.constant.ExceptionMessages.CAR_WITH_THIS_ID_DOES_NOT_EXIST;
import static project.constant.GlobalConstants.CAR;
import static project.constant.GlobalConstants.CARS;
import static project.constant.GlobalConstants.ID;
import static project.constant.GlobalConstants.MESSAGE;
import static project.constant.URL.ADD;
import static project.constant.URL.EDIT;
import static project.constant.URL.REMOVE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.constant.URL;
import project.model.domain.Car;
import project.model.dto.CarDto;
import project.model.exception.EntityAlreadyExists;
import project.model.service.CarService;
import project.validator.CarValidator;

@Controller
public class CarController {

    private CarService carService;
    private CarValidator carValidator;

    @Autowired
    public CarController(CarService carService, CarValidator carValidator) {
        this.carService = carService;
        this.carValidator = carValidator;
    }

    @GetMapping(URL.CARS)
    public String getAllCars(Model model) {
        model.addAttribute(CARS, carService.getAllCars());
        if (!model.containsAttribute(CAR)) {
            model.addAttribute(CAR, new Car());
        }

        return "admin/all_cars";
    }

    @PostMapping(URL.CARS + ADD)
    public String addCar(@ModelAttribute(CAR) CarDto carDTO, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        carValidator.validate(carDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.car", bindingResult);
            redirectAttributes.addFlashAttribute(CAR, carDTO);
            return "redirect:/cars";
        }
        try {
            carService.addCar(carDTO);
        } catch (EntityAlreadyExists e) {
            redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
            redirectAttributes.addFlashAttribute(CAR, carDTO);
        }

        return "redirect:/cars";
    }

    @RequestMapping(URL.CARS + REMOVE + URL.ID)
    public String removeCar(@PathVariable(ID) Integer id) {
        carService.deleteCarById(id);

        return "redirect:/cars";
    }

    @RequestMapping(URL.CARS + EDIT + URL.ID)
    public String editCar(@PathVariable(ID) Integer id, Model model) {
        Optional<Car> car = carService.getCarById(id);
        if (car.isPresent()) {
            model.addAttribute(CAR, car.get());
        } else {
            model.addAttribute(MESSAGE, CAR_WITH_THIS_ID_DOES_NOT_EXIST);
            model.addAttribute(CAR, new Car());
        }
        model.addAttribute(CARS, carService.getAllCars());

        return "admin/all_cars";
    }
}
