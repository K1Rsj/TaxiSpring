package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.domain.CarType;
import project.model.service.CarTypeService;

import java.util.Optional;

@Controller
public class CarTypeController {

    private CarTypeService carTypeService;

    @Autowired
    public CarTypeController(CarTypeService carTypeService) {
        this.carTypeService = carTypeService;
    }

    @GetMapping("/types")
    public String getAllCarTypes(Model model) {
        model.addAttribute("carTypes", carTypeService.getAllCarTypes());
        model.addAttribute("carType", new CarType());

        return "user/all_car_types";
    }

    @PostMapping("/types/add")
    public String addCarType(@ModelAttribute("carType") CarType carType, Model model) {
        carTypeService.addCarType(carType);

        return "redirect:/types";
    }

    @RequestMapping("/types/remove/{id}")
    public String removeCarType(@PathVariable("id") Integer id) {
        carTypeService.deleteCarTypeById(id);

        return "redirect:/types";
    }

    @RequestMapping("/types/edit/{id}")
    public String editCarType(@PathVariable("id") Integer id, Model model) {
        Optional<CarType> carType = carTypeService.getCarTypeById(id);
        if (carType.isPresent()) {
            model.addAttribute("carType", carType.get());
        } else {
            model.addAttribute("message", "Car type with that id doesn't exist");
            model.addAttribute("carType", new CarType());
        }
        model.addAttribute("carTypes", carTypeService.getAllCarTypes());

        return "user/all_car_types";
    }
}
