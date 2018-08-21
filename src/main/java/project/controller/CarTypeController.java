package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.model.service.CarTypeService;

@Controller
public class CarTypeController {

    private CarTypeService carTypeService;

    @Autowired
    public CarTypeController(CarTypeService carTypeService) {
        this.carTypeService = carTypeService;
    }

    @GetMapping(value = "/")
    public String getAllCarTypes(Model model) {
        model.addAttribute("carTypes", carTypeService.getAllCarTypes());
        return "user/all_car_types";
    }
}
