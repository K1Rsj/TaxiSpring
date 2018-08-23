package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping({"/", "/home"})
    public String getMainPage() {

        return "index";
    }

    @RequestMapping("/registration")
    public String getRegistrationPage() {

        return "user/user_registration_page";
    }
}
