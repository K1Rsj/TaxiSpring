package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.domain.Car;
import project.model.service.CarService;

import java.util.Optional;

@Controller
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getAllCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("car", new Car());

        return "admin/all_cars";
    }

    @PostMapping("/cars/add")
    public String addCar(@ModelAttribute("car")Car car, @ModelAttribute("type") String type) {
        carService.addCar(car, type);

        return "redirect:/cars";
    }

    @RequestMapping("/cars/remove/{id}")
    public String removeCar(@PathVariable("id") Integer id) {
        carService.deleteCarById(id);

        return "redirect:/cars";
    }

    @RequestMapping("/cars/edit/{id}")
    public String editCar(@PathVariable("id") Integer id, Model model) {
        Optional<Car> car = carService.getCarById(id);
        if (car.isPresent()) {
            model.addAttribute("car", car.get());
        }
        else {
            model.addAttribute("message", "Car with this id doesn't exist");
            model.addAttribute("car", new Car());
        }
        model.addAttribute("cars", carService.getAllCars());

        return "admin/all_cars";
    }
}
