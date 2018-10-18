package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping({"/", "/home"})
    public String getMainPage() {

        return "index";
    }

    @GetMapping("/login")
    public String logIn(@RequestParam(value = "logout",	required = false) String logout,
                        @RequestParam(value = "error", required = false) String error, Model model) {
        if (logout != null) {
            model.addAttribute("informationMessage", "Logged out successfully");

            return "index";
        }
        if (error != null) {
            model.addAttribute("informationMessage", "Username or password is incorrect.");
        }

        return "form/login_form";
    }

    @GetMapping("/user_home")
    String getUserMainPage() {

        return "user/user_foundation";
    }

    @GetMapping("/admin_home")
    String getAdminMainPage() {

        return "admin/admin_foundation";
    }
}
